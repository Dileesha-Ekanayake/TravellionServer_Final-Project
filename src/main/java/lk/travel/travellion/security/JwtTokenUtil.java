package lk.travel.travellion.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class for managing JSON Web Tokens (JWT).
 * This class provides methods to generate and validate JWT tokens,
 * extract claims, and work with token expiration.
 */
@Service
public class JwtTokenUtil {

    private final String secret;
    private final int expiration;

    /**
     * Constructs a new instance of JwtTokenUtil with the specified secret and expiration values.
     *
     * @param secret the secret key used to sign the JWT tokens
     * @param expiration the expiration time for the JWT tokens in seconds
     */
    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") int expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    /**
     * Provides a bean of the JwtTokenUtil utility class, configured with the
     * necessary secret and expiration properties for managing JSON Web Tokens (JWT).
     *
     * @return an instance of JwtTokenUtil initialized with the application's secret and expiration settings
     */
    @Bean
    public JwtTokenUtil getTokenUtil() {
        return new JwtTokenUtil(secret, expiration);
    }

    /**
     * Generates a JSON Web Token (JWT) for the specified user.
     * The token includes claims for the user's authorities and other standard token details,
     * such as subject, issue date, and expiration date.
     *
     * @param userDetails the user details containing information about the user, such as username and authorities
     * @return a JWT as a string containing encoded information about the user
     */
    // Generate JWT Token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("aud", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a JSON Web Token (JWT) for a given username.
     * The token is built using the provided username and includes claims,
     * an issue time, and an expiration time.
     *
     * @param userName the username for which the token is to be generated
     * @return a JWT as a string
     */
    // Generate JWT Token -> OAuth2
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("aud", userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
//                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token from which the username will be extracted
     * @return the username extracted from the token
     */
    // Extract username from JWT Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token using a provided claim resolver function.
     *
     * @param <T> the type of the claim to be extracted
     * @param token the JWT token from which the claim needs to be extracted
     * @param claimsResolver a function to resolve and retrieve a specific claim from the parsed claims
     * @return the extracted claim of type T
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a given JWT token.
     *
     * @param token the JWT token from which claims are to be extracted
     * @return the {@code Claims} object containing all claims present in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validates a JSON Web Token (JWT) against user details by checking if the username
     * in the token matches the provided user details and if the token is not expired.
     *
     * @param token the JWT to validate
     * @param userDetails the user details to validate the token against
     * @return true if the token is valid, false otherwise
     */
    // Validate JWT Token
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if the given JWT token is expired by comparing its expiration date
     * with the current date.
     *
     * @param token the JWT token to be checked for expiration
     * @return true if the token is expired, false otherwise
     */
    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a given JWT token.
     *
     * @param token the JWT token from which the expiration date is to be extracted
     * @return the expiration date of the token as a {@link Date} object
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Retrieves and constructs the signing key used for JWT creation and validation.
     * The key is decoded from a Base64-encoded secret string and transformed into a secure HMAC (Hash-based Message Authentication Code)
     * SHA key suitable for the specified token signature algorithm.
     *
     * @return the signing key used for creating and validating JWT tokens
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
