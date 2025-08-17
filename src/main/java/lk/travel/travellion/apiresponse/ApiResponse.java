package lk.travel.travellion.apiresponse;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

/**
 * Represents a generic API response model for standardizing HTTP responses throughout the application.
 * This class provides flexibility by allowing the inclusion of generic data and
 * encapsulates essential response elements like status code, message, and timestamp.
 *
 * @param <T> The type of the response payload encapsulated within the API response.
 */
@Data
@Builder
public class ApiResponse<T> {

    private String message;
    private Integer statusCode;
    private Instant timestamp;
    private T data;

    /**
     * Generates a standardized HTTP response wrapped in a {@code ResponseEntity} containing an {@code ApiResponse}.
     * This method allows for including a message, status, and generic data in the response.
     *
     * @param <T>    The type of the response payload encapsulated within the {@code ApiResponse}.
     * @param data   The data to be included in the response payload.
     * @param message A message describing the response or its outcome.
     * @param status The HTTP status to be included in the response.
     * @return A {@code ResponseEntity} containing the {@code ApiResponse} object.
     */
    private static <T> ResponseEntity<ApiResponse<T>> generateResponse(T data, String message, HttpStatus status) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .message(message)
                .statusCode(status.value())
                .timestamp(Instant.now())
                .data(data)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Creates a standardized success response encapsulating a message, HTTP status, and optional payload.
     *
     * @param <T>     The type of the data included in the response.
     * @param message The success message to be included in the response.
     * @param status  The HTTP status code to be returned with the response.
     * @param data    The payload or data to be included in the API response.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} object with the provided message, status, and data.
     */
    public static <T> ResponseEntity<ApiResponse<T>> successResponse(String message, HttpStatus status, T data) {
        return generateResponse(data, message, status);
    }

    /**
     * Creates a successful API response with a default success message and HTTP status.
     *
     * @param <T>  The type of the response payload.
     * @param data The payload or data to be included in the response.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} with the provided data,
     *         a "Success" message, and an HTTP status of 200 (OK).
     */
    public static <T> ResponseEntity<ApiResponse<T>> successResponse(T data) {
        return generateResponse(data, "Success", HttpStatus.OK);
    }

    /**
     * Generates a standardized error response wrapped in a {@code ResponseEntity} with the
     * provided error message, HTTP status, and optional data.
     *
     * @param <T>    the type of the response payload encapsulated within the {@code ApiResponse}
     * @param message the error message to be included in the response, providing details
     *                about the failure
     * @param status  the HTTP status to be returned with the response, indicating the
     *                nature of the error
     * @param data    the optional additional data or context to be included with the
     *                response, such as error-related information
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} object with
     *         the provided error message, status code, and data
     */
    public static <T> ResponseEntity<ApiResponse<T>> errorResponse(String message, HttpStatus status, T data) {
        return generateResponse(data, message, status);
    }
}
