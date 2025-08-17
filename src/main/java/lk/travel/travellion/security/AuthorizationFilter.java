package lk.travel.travellion.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * AuthorizationFilter is a Spring Security filter that intercepts incoming HTTP requests
 * to verify the presence and validity of a JWT token in the Authorization header.
 * It ensures that only authenticated users with a valid token are provided access.
 *
 * The class extends OncePerRequestFilter, ensuring that the filter is executed
 * once per request within a single request lifecycle. It integrates with Spring's
 * SecurityContextHolder to manage authentication.
 *
 * Responsibilities:
 * - Extract and verify the JWT token from the incoming request's Authorization header.
 * - Extract the username from the token and validate the token using JwtTokenUtil.
 * - Authenticate the user by setting the authentication details in the SecurityContext.
 * - If no valid token is provided, the filter delegates the request further in the filter chain.
 *
 * Dependencies:
 * - JwtTokenUtil: Utility for extracting, validating, and managing JWT tokens.
 * - UserDetailsService: Service to load user-specific data.
 *
 * Methods:
 * - doFilterInternal: Core method to process the filter logic, including token validation and user authentication.
 */
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userService;

    /**
     * Filters incoming HTTP requests to validate JWT-based authorization.
     * Extracts and validates the JWT from the `Authorization` header.
     * If the JWT token is valid, it establishes the user's authentication in the security context.
     * If no valid token is found, it simply continues the filter chain without altering authentication.
     *
     * @param request the HTTP request object containing client request information
     * @param response the HTTP response object for sending responses to the client
     * @param chain the filter chain for processing the request
     * @throws IOException in case of I/O errors during request processing
     * @throws ServletException in case of any servlet-related errors during request processing
     */
    @Override
    public void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain chain
    ) throws IOException, ServletException {

        // Check Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Extract token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            // Extract username from token
            String username = jwtTokenUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load UserDetails
                UserDetails userDetails = userService.loadUserByUsername(username);

                // Validate the token
                boolean validToken = jwtTokenUtil.validateToken(token, userDetails);

                if (validToken) {
                    // Create authentication object
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    // Set authentication details from request
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error processing the JWT: " + e.getMessage());
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

}
