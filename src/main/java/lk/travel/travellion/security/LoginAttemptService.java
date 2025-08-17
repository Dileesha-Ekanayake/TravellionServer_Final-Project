package lk.travel.travellion.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for tracking and managing login attempts based on IP addresses.
 * This service is used to monitor login failures and block IP addresses after
 * a certain number of failed attempts.
 */
@Service
public class LoginAttemptService {
    /**
     * A thread-safe map that keeps track of login attempts per IP address.
     * The key represents the IP address as a string, and the value represents the number of login attempts.
     * This map is used to monitor and limit repeated login failures to enhance security.
     */
    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();

    /**
     * Tracks a login failure attempt for a specified IP address.
     * If the IP address has existing failed attempts, the count is incremented.
     * Otherwise, a new record for the IP address is created with an initial count of 1.
     *
     * @param ip the IP address for which the login failure attempt is being recorded
     */
    public void loginFailed(String ip) {
        attempts.merge(ip, 1, Integer::sum);
    }

    /**
     * Checks if the given IP address is blocked due to excessive failed login attempts.
     *
     * @param ip the IP address to check for blocked status
     * @return true if the IP address is blocked; false otherwise
     */
    public boolean isBlocked(String ip) {
        return attempts.getOrDefault(ip, 0) >= 5;
    }
}

