package lk.travel.travellion.security;

import lk.travel.travellion.entity.User;
import lk.travel.travellion.projection.UserAccountLocked;
import lk.travel.travellion.repository.ModuleRepository;
import lk.travel.travellion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service implementation for handling user authentication and details retrieval
 * required by Spring Security. This class implements the {@link UserDetailsService}
 * interface, providing methods to authenticate users and retrieve user-specific
 * details, such as authorities and account state.
 *
 * Dependencies:
 * - {@link ModuleRepository} is used to fetch system modules for determining administrative authorities.
 * - {@link UserRepository} is used to retrieve user details and account state from the database.
 *
 * Configuration:
 * - The service supports an in-memory user with a username and password defined
 *   via properties `spring.security.user.name` and `spring.security.user.password`.
 *
 * Responsibilities:
 * - Load user details by username and generate a {@link UserDetails} object.
 * - Differentiate between in-memory administrative users and database-stored users.
 * - Provide built-in support for user account locking and privilege-based authority management.
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    /**
     * A repository interface for performing operations on system modules.
     * This is used within the {@link MyUserDetailsService} class to retrieve all
     * available system modules and assign administrative privileges based on module operations.
     *
     * The data retrieved from this repository is essential for defining security
     * authorities and granting permissions for specific actions on system modules.
     *
     * In the context of {@link MyUserDetailsService}, this repository enables:
     * - Fetching all modules to determine operation permissions, such as select, insert, update, and delete.
     * - Dynamically constructing authority strings for administrators using module names and operations.
     *
     * Usage of this repository ensures modular and extendable security configurations
     * within the broader context of the application.
     */
    private final ModuleRepository moduleRepository;

    /**
     * Represents the repository used to interact with user-related data in the database.
     * This repository is responsible for performing CRUD operations and custom queries
     * associated with the {@code User} entity. It is a central dependency in the
     * {@code MyUserDetailsService} class to retrieve user details for authentication
     * and authorization purposes.
     *
     * Key Responsibilities:
     * - Retrieve user information by username for authentication.
     * - Check for existence of specific usernames (with or without ID exclusions).
     * - Determine account locking status for a given user.
     * - Fetch users based on specific criteria such as employee association,
     *   account lock state, or user status.
     */
    private final UserRepository userRepository;

    /**
     * Represents the username of an in-memory administrative user for authentication purposes.
     * This value is configured via the `spring.security.user.name` property in the application configuration.
     * It is used within the authentication process to differentiate between in-memory users and users stored in the database.
     */
    @Value("${spring.security.user.name}")
    private String inMemoryUserName;

    /**
     * Represents the in-memory user's password utilized for authentication in the application.
     * This value is injected from the `spring.security.user.password` property defined in the
     * application's configuration.
     *
     * It is specific to the in-memory administrative user and is used to authenticate
     * requests alongside the in-memory username.
     */
    @Value("${spring.security.user.password}")
    private String inMemoryUserPassword;

    /**
     * Loads the user details associated with the specified username.
     *
     * This method retrieves a user by the given username and constructs a {@code UserDetails}
     * object containing the user's properties and granted authorities. If the username is not
     * found, a {@code UsernameNotFoundException} is thrown.
     *
     * @param username the username provided for locating the user
     * @return a {@code UserDetails} object containing the user's information and authorities
     * @throws UsernameNotFoundException if no user with the specified username is found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User foundUser = findUserByUsername(username);
        return buildUserDetails(foundUser);
    }

    /**
     * Searches for a user entity based on the provided username. If the username matches
     * the preconfigured in-memory username, an in-memory user is created and returned.
     * Otherwise, a lookup is performed in the database to retrieve the user details.
     *
     * @param username the username of the user to be searched
     * @return the {@code User} entity corresponding to the provided username
     * @throws UsernameNotFoundException if no user is found with the provided username
     */
    private User findUserByUsername(String username) {
        if (username.equals(inMemoryUserName)) {
            return createInMemoryUser();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    /**
     * Builds a {@link UserDetails} object from the provided {@link User} entity.
     * This method configures the user's account settings (e.g., locked, expired)
     * and assigns roles or authorities based on their username and stored privileges.
     *
     * @param user the user entity containing necessary information such as username,
     *             password, and roles to be converted into a {@link UserDetails} object.
     * @return a {@link UserDetails} object configured with the user's data and authorities.
     */
    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getUsername().equals(inMemoryUserName) ?
                        getAdminAuthorities() : getUserAuthorities(user))
                .accountExpired(false)
                .accountLocked(isUserAccountLocked(user.getUsername()))
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    /**
     * Creates and returns a new in-memory user object using the predefined
     * username and password configured in application properties.
     *
     * @return a {@link User} instance representing the in-memory user with the configured
     *         username and password.
     */
    // Create an in-memory user
    private User createInMemoryUser() {
        return User.builder().username(inMemoryUserName).password(inMemoryUserPassword).build();
    }

    /**
     * Generates a set of administrative authorities based on system modules and their standard operations.
     * The method retrieves a collection of modules and assigns a set of permissions for each module.
     * In the case of a module named "user", the additional "lock" operation is appended to the standard operations.
     * Each authority is identified by a combination of the module name and operation in the format "moduleName-operation".
     *
     * @return a set of {@link SimpleGrantedAuthority} instances representing the administrative authorities for all modules.
     */
    protected Set<SimpleGrantedAuthority> getAdminAuthorities() {
        var standardOperations = Set.of("select", "insert", "update", "delete");
        return moduleRepository.findAll().stream()
                .flatMap(module -> {
                    var operations = module.getName().equalsIgnoreCase("user")
                            ? Stream.concat(standardOperations.stream(), Stream.of("lock"))
                            : standardOperations.stream();

                    return operations.map(op ->
                            new SimpleGrantedAuthority(module.getName().toLowerCase() + "-" + op));
                })
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the authorities associated with a given user from the database.
     * The authorities are derived from the user's roles and their associated privileges.
     *
     * @param user The {@link User} entity containing roles and privileges information.
     * @return A set of {@link SimpleGrantedAuthority} instances representing the user's authorities.
     */
    // Retrieve Database user authorities
    private Set<SimpleGrantedAuthority> getUserAuthorities(User user) {
        // Use the role and privilege information to define authorities
        return user.getUserroles().stream()
                .flatMap(userRole -> userRole.getRole().getPrivileges().stream())
                .map(privilege -> new SimpleGrantedAuthority(privilege.getAuthority()))
                .collect(Collectors.toSet());
    }

    /**
     * Determines whether the user account is locked based on the specified username.
     * This method differentiates between an in-memory user (always unlocked) and a
     * user fetched from the database. For database users, it queries the repository
     * to check the account locked status.
     *
     * @param userName the username of the account to be checked
     * @return {@code true} if the user account is locked; {@code false} otherwise
     */
    // User Account lock feature
    protected boolean isUserAccountLocked(String userName) {
        if (userName.equalsIgnoreCase(inMemoryUserName)) {
            return false;
        }

        return Optional.ofNullable(userRepository.findUserAccountLockedByUsername(userName))
                .map(UserAccountLocked::getAccountLocked)
                .orElse(false);
    }

}
