package lk.travel.travellion.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Custom AuthenticationProvider implementation for providing authentication logic
 * using username and password with added security measures such as login attempt tracking
 * and account lock features.
 *
 * This class works with {@link MyUserDetailsService} to load user details,
 * validates login attempts for brute-force prevention, and handles account-level restrictions.
 *
 * The class uses a cache to track login attempts and prevents authentication
 * after a configurable number of failed attempts until the lockout period expires.
 *
 * Components and services collaborating with this class:
 * - {@link MyUserDetailsService}: Loads user details from the database or in-memory storage.
 * - {@link LoginAttemptService}: Tracks and blocks login attempts based on IP address.
 * - {@link PasswordEncoder}: Encodes and validates password hashes during authentication.
 *
 * Major features:
 * - Support for both in-memory and database-stored users.
 * - Login attempt tracking and temporary account lock support.
 * - Block IP addresses after exceeding failed-login thresholds.
 * - Password validation using a configurable {@link PasswordEncoder}.
 * - Account lock detection and administration.
 *
 * Notes on Configuration:
 * - `MAX_ATTEMPTS` specifies the maximum number of allowed failed login attempts per username.
 * - `CACHE_EXPIRY_SECONDS` defines the duration in seconds before the failed login attempt cache is reset.
 *
 * Thread Safety:
 * The class uses thread-safe constructs like Google's LoadingCache
 * and {@link LoginAttemptService} to manage concurrent login attempts and blocking operations.
 *
 * Exceptions Thrown:
 * - {@link BadCredentialsException}: Thrown when invalid credentials are provided.
 * - {@link UsernameNotFoundException}: Thrown when the specified user does not exist.
 * - {@link LockedException}: Raised for temporary or permanent account locks.
 * - {@link AuthenticationException}: Indicates a common authentication process failure.
 * - {@link AuthenticationServiceException}: Thrown when service or infrastructure fails during authentication.
 */
@Component
@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider {

    /**
     * The maximum number of login attempts allowed before triggering additional action
     * (e.g., locking the user out or applying rate limiting).
     *
     * This value is used as part of login attempt validation to enhance user experience
     * and security by allowing a limited number of retries before intervention.
     *
     * In this implementation, the value is set to 3 to provide users with extra flexibility
     * compared to prior configurations while maintaining security.
     */
    private static final int MAX_ATTEMPTS = 3; // Increased from 1 for better UX
    /**
     * The duration for which authentication-related data is cached, in seconds.
     * This constant is used to determine the expiration time of cached items,
     * ensuring they are refreshed or removed after the specified duration.
     */
    private static final int CACHE_EXPIRY_SECONDS = 5; // 5 seconds
    
    /**
     * A reference to the {@link MyUserDetailsService}, which is responsible for retrieving
     * and managing user details for authentication purposes.
     *
     * This service provides user-specific data required for the application's
     * authentication mechanism, including loading users by their username
     * and managing user roles, privileges, and account states (e.g.,
     * account locked, disabled, etc.).
     *
     * This is a final variable to ensure immutability and thread-safety
     * within the {@code MyAuthenticationProvider}.
     */
    private final MyUserDetailsService myUserDetailsService;
    /**
     * Service used for tracking and managing login attempts through the application.
     * This variable is an instance of {@code LoginAttemptService}, which facilitates
     * monitoring repeated failed login attempts by users based on their IP addresses.
     *
     * The service helps in improving security by potentially restricting access
     * after a configurable number of failed login attempts, which can mitigate
     * brute force attacks.
     */
    private final LoginAttemptService loginAttemptService;
    /**
     * Encoder used for securing passwords by applying a one-way encryption or hashing mechanism.
     * This field is typically injected to enable password encoding and matching during authentication processes.
     * It ensures that plain-text passwords are stored securely and protects against unauthorized access.
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * A cache for tracking authentication attempts per user, where the key is typically a
     * username or identifier, and the value represents the number of failed login attempts.
     * This cache is leveraged to enforce account locking or throttling mechanisms based on policy.
     *
     * The cache is a {@code LoadingCache} implementation, allowing automatic eviction of entries
     * after a specified time, aiding in efficient memory management and ensuring that old or unused
     * data is removed without manual intervention.
     *
     * Use cases include:
     * - Mitigating brute-force attacks by limiting login attempts.
     * - Tracking failed login attempts for audit or reporting purposes.
     */
    private LoadingCache<String, Integer> attemptsCache;

    /**
     * Constructs a new instance of {@code MyAuthenticationProvider}.
     * This authentication provider handles user authentication, integrates login attempt tracking,
     * and delegates user details retrieval and password validation.
     *
     * @param myUserDetailsService the user details service responsible for loading user data
     * @param loginAttemptService the service for tracking and managing login attempts
     * @param passwordEncoder the password encoder used for validating user credentials
     */
    public MyAuthenticationProvider(
            MyUserDetailsService myUserDetailsService,
            LoginAttemptService loginAttemptService,
            PasswordEncoder passwordEncoder) {
        this.myUserDetailsService = myUserDetailsService;
        this.loginAttemptService = loginAttemptService;
        this.passwordEncoder = passwordEncoder; // Inject instead of creating new instance
    }

    /**
     * Initializes the `attemptsCache` used to track login attempts and their expiration settings.
     * This method is annotated with {@code @PostConstruct}, ensuring it is run after
     * the dependency injection is complete and the bean is fully initialized.
     *
     * The cache is configured with the following properties:
     * - Entries expire after a specified number of seconds defined by {@code CACHE_EXPIRY_SECONDS}.
     * - A maximum size of 10,000 entries to prevent memory leaks.
     * - Statistics recording enabled for monitoring cache operations.
     *
     * The cache's default behavior initializes each entry with a value of 0 when accessed for the first time.
     */
    @PostConstruct
    public void init() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(CACHE_EXPIRY_SECONDS, TimeUnit.SECONDS)
                .maximumSize(10000) // Prevent memory leaks
                .recordStats() // Enable statistics
                .build(CacheLoader.from(key -> 0));
    }

    /**
     * Authenticates the provided authentication object. Validates the login attempts
     * based on the client's IP address and credentials. If the user exceeds the allowed login
     * attempts or provides invalid credentials, appropriate exceptions are thrown.
     *
     * @param authentication the authentication object containing username and credentials
     *                        to be verified
     * @return an authenticated Authentication object if authentication succeeds
     * @throws AuthenticationException if authentication fails due to invalid credentials,
     *                                  locked accounts, or other issues during the process
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = Optional.ofNullable(authentication.getCredentials())
                .map(Object::toString)
                .orElseThrow(() -> new BadCredentialsException("No credentials provided"));
        String clientIP = getClientIP();

        try {
            validateLoginAttempts(clientIP, username);
            return performAuthentication(username, password, clientIP);
        } catch (ExecutionException e) {
//            log.error("Authentication cache error for user: {}", username, e);
            loginAttemptService.loginFailed(clientIP);
            throw new AuthenticationServiceException("Authentication service error", e);
        }
    }

    /**
     * Validates login attempts for a given client IP and username.
     * It checks if the IP address is blocked due to too many failed attempts
     * or if the username has exceeded the allowed maximum attempts, and throws
     * exceptions accordingly.
     *
     * @param clientIP the IP address of the client attempting to log in
     * @param username the username of the account attempting to log in
     * @throws ExecutionException if an exception occurs while retrieving attempts from the cache
     * @throws LockedException if the IP is blocked or the account is locked due to failed attempts
     */
    private void validateLoginAttempts(String clientIP, String username) throws ExecutionException {
        if (loginAttemptService.isBlocked(clientIP)) {
//            log.warn("IP address blocked: {}", clientIP);
            throw new LockedException("Too many failed attempts from this IP. Try again later.");
        }

        int attempts = attemptsCache.get(username);
        if (attempts >= MAX_ATTEMPTS) {
//            log.warn("Account temporary locked due to failed attempts: {}", username);
            throw new LockedException(
                    String.format("Account is locked. Please try again after %d seconds", 
                                CACHE_EXPIRY_SECONDS));
        }
    }

    /**
     * Performs authentication for a user based on the provided credentials and client IP address.
     *
     * Validates the username and password against stored user details.
     * Checks if the user account is locked. If the authentication is successful,
     * it returns an authentication token. Otherwise, it handles failed attempts
     * and throws appropriate exceptions.
     *
     * @param username    the username provided by the client for authentication
     * @param password    the raw password input provided by the client
     * @param clientIP    the client's IP address used for tracking failed attempts
     * @return an {@code Authentication} instance if authentication is successful
     * @throws LockedException if the user account is permanently locked
     * @throws BadCredentialsException if the provided username or password is invalid
     * @throws UsernameNotFoundException if the provided username does not exist
     */
    private Authentication performAuthentication(String username, String password, String clientIP) {
        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            
            if (myUserDetailsService.isUserAccountLocked(username)) {
//                log.warn("Attempt to access permanently locked account: {}", username);
                throw new LockedException("This account has been locked. Please contact an administrator.");
            }

            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                attemptsCache.invalidate(username);
//                log.info("Successful authentication for user: {}", username);
                return new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
            }

            handleFailedAttempt(username, clientIP);
            throw new BadCredentialsException("Invalid credentials");

        } catch (UsernameNotFoundException e) {
            handleFailedAttempt(username, clientIP);
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    /**
     * Processes a failed login attempt by incrementing the failure count
     * for both the user and the associated client IP address. Updates the
     * cache of failed attempts and logs errors in case of issues with the cache.
     *
     * @param username the username for which the login attempt failed
     * @param clientIP the IP address from which the failed login attempt originated
     */
    private void handleFailedAttempt(String username, String clientIP) {
        try {
            loginAttemptService.loginFailed(clientIP);
            int attempts = attemptsCache.get(username);
            attemptsCache.put(username, attempts + 1);
//            log.warn("Failed login attempt for user: {}. Attempt #{}", username, attempts + 1);
        } catch (ExecutionException e) {
            log.error("Error updating failed attempts cache", e);
        }
    }

    /**
     * Determines whether this {@link AuthenticationProvider}
     * supports the specified authentication class.
     *
     * @param authentication the class of the authentication object to be checked
     * @return {@code true} if the specified authentication class is supported,
     *         {@code false} otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Retrieves the client's IP address from the current HTTP request.
     * If the "X-Forwarded-For" header is present in the request, the first IP in the header is returned.
     * Otherwise, the remote address from the request object is used.
     *
     * @return the client's IP address as a string
     * @throws AuthenticationServiceException if no request context is available
     */
    private String getClientIP() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes == null) {
            throw new AuthenticationServiceException("No request context found");
        }

        HttpServletRequest request = attributes.getRequest();
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}
