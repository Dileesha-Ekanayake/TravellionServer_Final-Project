package lk.travel.travellion.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown to indicate that the input provided by the user is invalid or does
 * not meet the expected requirements. This exception is typically used when user-provided
 * data cannot be processed due to validation issues or malformed inputs.
 *
 * The HTTP status code associated with this exception is 400 Bad Request.
 */
@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {

    private final int errorCode = 400;
    private final String additionalData;

    /**
     * Constructs a new InvalidInputException with the specified detail message and additional data.
     *
     * @param message the detail message explaining the reason for the exception
     * @param additionalData additional context or information related to the invalid input
     */
    public InvalidInputException(String message, String additionalData) {
        super(message);
        this.additionalData = additionalData;
    }

    /**
     * Constructs a new InvalidInputException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidInputException(String message) {
        super(message);
        this.additionalData = null;
    }
}
