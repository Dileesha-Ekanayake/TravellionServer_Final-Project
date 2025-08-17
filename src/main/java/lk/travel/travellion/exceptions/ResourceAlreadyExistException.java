package lk.travel.travellion.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown to indicate that a resource already exists and cannot be created
 * again. This is typically used in scenarios where an attempt is made to create a
 * resource that violates a uniqueness constraint.
 *
 * The HTTP status code associated with this exception is 409 Conflict.
 */
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistException extends RuntimeException {

    private final int errorCode;
    private final String additionalData;

    /**
     * Constructs a new ResourceAlreadyExistException with the specified detail message and
     * additional data. This exception is used to indicate that an attempt was made to create
     * a resource that already exists, violating a uniqueness constraint.
     *
     * @param message the detail message explaining the reason for the exception
     * @param additionalData additional context or information related to the resource conflict
     */
    public ResourceAlreadyExistException(String message, String additionalData) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT.value();
        this.additionalData = additionalData;
    }

    /**
     * Exception thrown when a resource already exists.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public ResourceAlreadyExistException(String message) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT.value();
        this.additionalData = null;
    }
}
