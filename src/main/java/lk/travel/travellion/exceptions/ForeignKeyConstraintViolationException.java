package lk.travel.travellion.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown to indicate that a foreign key constraint violation occurred during
 * a database operation. This exception is typically used when an attempt to insert,
 * update, or delete a record fails due to foreign key restrictions being enforced
 * at the database level.
 *
 * The HTTP status code associated with this exception is 409 Conflict.
 */
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ForeignKeyConstraintViolationException extends RuntimeException {

    private final int errorCode;
    private final String additionalData;

    /**
     * Constructs a new ForeignKeyConstraintViolationException with the specified detail message
     * and additional data. This exception is typically used to indicate that an operation
     * failed due to a foreign key constraint violation in the database.
     *
     * @param message the detail message explaining the reason for the exception
     * @param additionalData additional context or information related to the exception
     */
    public ForeignKeyConstraintViolationException(String message, String additionalData) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT.value(); // 409 for conflict
        this.additionalData = additionalData;
    }

    /**
     * Constructs a new ForeignKeyConstraintViolationException with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public ForeignKeyConstraintViolationException(String message) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT.value();
        this.additionalData = null;
    }
}
