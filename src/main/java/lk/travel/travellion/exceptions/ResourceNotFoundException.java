package lk.travel.travellion.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown to indicate that a requested resource could not be found. This exception
 * is typically used when a client attempts to access a resource that does not exist or
 * cannot be located.
 *
 * The HTTP status code associated with this exception is 404 Not Found.
 */
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final Integer errorCode = 404;
    private final String additionalData;

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message and additional data.
     *
     * @param message the detail message explaining the reason for the exception
     * @param additionalData additional context or information related to the resource not found
     */
    public ResourceNotFoundException(String message, String additionalData) {
        super(message);
        this.additionalData = additionalData;
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * This exception is used to indicate that a requested resource could not be found.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.additionalData = null;
    }

}
