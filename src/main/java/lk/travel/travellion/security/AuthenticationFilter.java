package lk.travel.travellion.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.travel.travellion.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * AuthenticationFilter is a custom filter for handling user authentication.
 * It extends the UsernamePasswordAuthenticationFilter and overrides its key methods
 * to provide JWT-based authentication.
 *
 * The filter processes login attempts by validating user credentials and generates a JWT token
 * upon successful authentication. The token is included in the response headers for
 * further use. In case of unsuccessful authentication, it handles error responses in JSON format.
 *
 * This filter works in conjunction with a custom implementation of {@code AuthenticationManager}
 * and the {@code JwtTokenUtil} class to perform authentication and token management.
 */
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * A reference to the {@link AuthenticationManager} implementation
     * used for processing and handling authentication requests.
     *
     * This variable serves as the central component for validating authentication attempts, coordinating with
     * configured authentication providers, and facilitating the management of authentication flows, such as
     * username-password validation or token-based authentication processes.
     *
     * Typically utilized within the authentication filter (e.g., {@code AuthenticationFilter}) to delegate
     * the responsibility of authenticating incoming requests and establishing the security context for successfully
     * authenticated entities.
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Utility class for handling operations related to JSON Web Tokens (JWT).
     * Designed to generate, validate, and extract data from JWTs used in the application.
     * This utility is utilized for authentication and authorization purposes.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Tries to authenticate the user based on the provided request credentials.
     * This method processes the request body to extract username and password,
     * creates an authentication token, and then delegates authentication to the
     * {@code AuthenticationManager}.
     *
     * @param request  the HTTP servlet request containing the user's login credentials
     * @param response the HTTP servlet response to be used for writing error messages or custom responses if needed
     * @return an {@code Authentication} object if authentication is successful
     * @throws AuthenticationException if authentication fails or the request format is invalid
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        setFilterProcessesUrl("/login");

        try {
            // Read and map the request body to LoginRequest
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            // Create authentication token with username and raw password
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            // Authenticate the user using AuthenticationManager
            return authenticationManager.authenticate(authRequest);

        } catch (IOException e) {
            // Only catch the IOException for invalid request format
            throw new AuthenticationServiceException("Invalid login request format", e);
        }
        // All AuthenticationExceptions propagate up to be handled by unsuccessfulAuthentication
    }

    /**
     * Sends an HTTP error response with a specified status code and message.
     *
     * @param response the HttpServletResponse object used to send the error response
     * @param status the HTTP status code to send
     * @param message the error message to include in the response
     */
    // Helper method to send error response
    private void sendErrorResponse(HttpServletResponse response, int status, String message) {
        try {
            response.sendError(status, message);
        } catch (IOException e) {
            throw new RuntimeException("Error sending error response", e);
        }
    }

    /**
     * Handles the actions to be performed upon successful authentication.
     * Generates a JWT token for the authenticated user and adds it to the response
     * in the Authorization header with a Bearer token format. Additionally, sets
     * headers to allow the client to access the Authorization header.
     *
     * @param request the HTTP request object containing the client's request details
     * @param response the HTTP response object to send the response back to the client
     * @param chain the filter chain to allow further processing of the request if necessary
     * @param authResult the Authentication object containing details of the authenticated user
     * @throws IOException if an input or output error is detected during the processing of the request
     * @throws ServletException if the request could not be handled
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);
        response.addHeader("Authorization", "Bearer " + token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

    }

    /**
     * Handles the actions to be taken upon unsuccessful authentication attempts.
     * This method is invoked when the authentication process fails.
     * It sets the HTTP status to 401 (Unauthorized) and returns a JSON-formatted error response with a custom message and status code.
     *
     * @param request  the {@code HttpServletRequest} object that contains the request the client made to the servlet
     * @param response the {@code HttpServletResponse} object that contains the response the servlet returns to the client
     * @param failed   the {@code AuthenticationException} that caused the authentication process to fail
     * @throws IOException      if an input or output error occurs while handling the response
     * @throws ServletException if the request could not be handled
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ApiResponse<Object> errorResponse = ApiResponse.<Object>builder()
                .message(failed.getMessage())
                .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                .data(null)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }

}
