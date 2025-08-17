package lk.travel.travellion.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


/**
 * SecurityConfiguration is a configuration class responsible for setting up the application's security settings.
 * It integrates Spring Security features such as method security, HTTP security, and user authentication mechanisms.
 * Additionally, it handles CORS configurations, JWT-based security filters, in-memory authentication, and default headers
 * to enforce robust system security policies.
 *
 * The class utilizes various Spring components and beans to define its security behavior, including:
 * - Setting up an {@link AuthenticationManager} to manage authentication providers.
 * - Creating a security filter chain to manage web security configurations such as CORS, CSRF, authorization rules, and session management.
 * - Integrating custom {@link AuthenticationProvider} for both in-memory and DAO-based authentication.
 * - Enabling JWT-based authentication and authorization through custom filters.
 * - Configuring an in-memory user with specified username and password, loaded via application properties.
 * - Enforcing security headers such as Content Security Policy (CSP) and frame options.
 *
 * This configuration ensures secure communication between the application and its clients while supporting
 * extensibility for custom authentication and authorization use cases.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    /**
     * Provides user details service for authentication and authorization purposes within the security configuration.
     * It interacts with the user details system to load user-specific data.
     *
     * This variable is marked final, ensuring that the reference cannot be changed
     * after initialization. The service is used by the application to retrieve user
     * details required for security operations.
     */
    private final MyUserDetailsService userDetailsService;
    /**
     * A utility instance of the {@code JwtTokenUtil} class that handles the generation,
     * validation, and extraction of information from JSON Web Tokens (JWT).
     * This is used to manage authentication tokens within the application and
     * encapsulates the logic for token operations.
     */
    private final JwtTokenUtil jwtTokenUtil;
    /**
     * A final and lazily initialized instance of MyAuthenticationProvider, utilized to
     * handle authentication-related logic such as validating user credentials and managing
     * authentication process within the security configuration.
     */
    @Lazy
    private final MyAuthenticationProvider authenticationProvider;
    /**
     * Represents the PasswordEncoder component responsible for encoding and validating passwords.
     * This field is typically used to apply a secure hashing function to plaintext passwords
     * to ensure secure password storage and comparison.
     *
     * The PasswordEncoder interface provides multiple encoding algorithms,
     * making it configurable for different security requirements.
     *
     * Marked as final to ensure immutability, indicating it cannot be reassigned
     * after it has been initialized.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Configuration property that specifies the allowed origins for Cross-Origin Resource Sharing (CORS).
     * The property value is injected from the application's external configuration using the
     * `app.cors.allowed-origins` key.
     *
     * This array contains the list of origins that are permitted to make cross-origin requests
     * to the application. Configuring allowed origins is essential for enabling or restricting
     * access to the backend from specific front-end origins.
     *
     * Example: Allowed origins could include URLs such as `http://example.com`, `http://localhost:3000`, etc.
     */
    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    /**
     * Represents the in-memory username used for authentication configuration.
     * The value is injected from the Spring property `spring.security.user.name`.
     */
    @Value("${spring.security.user.name}")
    private String inMemoryUserName;

    /**
     * Represents the password for the in-memory user configured in the application's security settings.
     * This value is injected using a property defined in the application's configuration, typically in an
     * external file such as `application.properties` or `application.yml`.
     *
     * The property key used to initialize this field is `spring.security.user.password`, which sets the
     * password for the default in-memory user employed by Spring Security.
     *
     * It is typically utilized for configurations that involve in-memory authentication, where user details,
     * such as username and password, are pre-defined for testing or simple applications.
     */
    @Value("${spring.security.user.password}")
    private String inMemoryUserPassword;

    /**
     * Configures and returns the security filter chain for the application.
     * This method defines various security settings such as CORS, CSRF, request authorization,
     * session management, and custom authentication and authorization filters.
     *
     * @param http the {@link HttpSecurity} object used to configure web-based security
     * @param authenticationManager the {@link AuthenticationManager} responsible for processing
     *                               authentication requests
     * @return a {@link SecurityFilterChain} instance representing the configured security filter chain
     * @throws Exception if an error occurs while building the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_CONFLICT);
                        })
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "*").permitAll() // Allow all pages to public (Only testing purpose)
                        .requestMatchers("/", "/**").permitAll() // Allow all pages to public (Only testing purpose)
                        .requestMatchers("/email/welcome").permitAll() // Allow test email service
                        .anyRequest().authenticated() // Allow only for authenticated users (remain only this at the time delivered)
                )
                .logout(LogoutConfigurer::permitAll)
                .addFilterBefore(new AuthenticationFilter(authenticationManager, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)  // JWT Filter
                .addFilterAfter(new AuthorizationFilter(jwtTokenUtil, userDetailsService), AuthenticationFilter.class)  // Authorization Filter for JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block")) // optional for legacy browsers
                                .contentSecurityPolicy(csp -> csp.policyDirectives(
                                        "default-src 'self'; " +
                                                "script-src 'self' https://cdnjs.cloudflare.com https://cdn.jsdelivr.net 'unsafe-inline' 'unsafe-eval'; " +
                                                "style-src 'self' https://fonts.googleapis.com https://cdnjs.cloudflare.com 'unsafe-inline'; " +
                                                "font-src 'self' https://fonts.gstatic.com; " +
                                                "img-src 'self' data: https:; " +
                                                "connect-src 'self' http://localhost:4200; " +
                                                "frame-ancestors 'self';"
                                ))
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    /**
     * Provides a bean of {@link InMemoryUserDetailsManager} configured with a hardcoded user.
     * The user details, such as username, password (encoded using the configured password encoder),
     * and granted authorities, are specified here.
     *
     * @return an instance of {@link InMemoryUserDetailsManager} containing a single user with
     *         defined credentials and authorities.
     */
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        User.UserBuilder userBuilder = User.builder();
        return new InMemoryUserDetailsManager(
                userBuilder
                        .username(inMemoryUserName)
                        .password(passwordEncoder.encode(inMemoryUserPassword))
                        .authorities(userDetailsService.getAdminAuthorities())
                        .build()
        );
    }

    /**
     * Configures and provides a customized {@code AuthenticationManager} bean.
     * The {@code AuthenticationManager} is built using multiple authentication providers,
     * including an in-memory authentication provider, a custom authentication provider,
     * and a database-based authentication provider. This manager is responsible for handling
     * authentication requests and delegating them to the appropriate provider.
     *
     * @param http the {@code HttpSecurity} instance used to configure the security features and retrieve shared objects
     * @return the configured {@code AuthenticationManager} instance
     * @throws Exception if any error occurs while building the {@code AuthenticationManager}
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(inMemoryAuthenticationProvider())  // Try in-memory provider first
                .authenticationProvider(authenticationProvider)  // Then try custom provider
                .authenticationProvider(daoAuthenticationProvider())  // Finally, try database provider
                .build();
    }

    /**
     * Creates and configures an in-memory implementation of the {@link AuthenticationProvider}.
     * This provider uses a local {@link InMemoryUserDetailsManager} to authenticate users and a custom
     * password encoder for matching provided credentials with the stored values.
     *
     * @return an instance of {@link AuthenticationProvider} configured for in-memory user authentication
     */
    @Bean
    public AuthenticationProvider inMemoryAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(inMemoryUserDetailsManager());
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    /**
     * Configures and returns a DaoAuthenticationProvider bean.
     * The DaoAuthenticationProvider uses the provided UserDetailsService and PasswordEncoder
     * to authenticate users and validates their credentials.
     *
     * @return an instance of AuthenticationProvider configured with the specified user details service
     *         and password encoder
     * @throws UsernameNotFoundException if the user details service fails to locate a user
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() throws UsernameNotFoundException {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    /**
     * Configures and provides a {@link CorsConfigurationSource} bean for managing Cross-Origin Resource Sharing (CORS) settings.
     * This method sets up the CORS configuration to allow specific origins, HTTP methods, headers, and credentials.
     *
     * @return a {@link CorsConfigurationSource} instance containing the configured CORS settings
     */
    @Bean
    CorsConfigurationSource corsConfigSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList(allowedOrigins));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

}
