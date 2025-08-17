package lk.travel.travellion.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown to indicate that a transaction has been rolled back due to specific
 * constraints or conflicts encountered during its execution. This exception is typically
 * used in scenarios where a transaction cannot be completed successfully and must be
 * reverted to maintain data integrity.
 *
 * The HTTP status code associated with this exception is 409 Conflict.
 */
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class TransactionRollbackException extends RuntimeException {

    private final int errorCode;
    private final String failedOperation;

    /**
     * Constructs a new TransactionRollbackException with the specified detail message,
     * failed operation context, and the underlying cause. This exception is used to
     * indicate that a transaction has been rolled back due to constraints or conflicts
     * encountered during its execution.
     *
     * @param message the detail message explaining the reason for the exception
     * @param failedOperation the operation or context that caused the rollback
     * @param cause the throwable that caused this exception
     */
    public TransactionRollbackException(String message, String failedOperation, Throwable cause) {
        super(message, cause);
        this.errorCode = HttpStatus.CONFLICT.value();
        this.failedOperation = failedOperation;
    }

    /**
     * Constructs a new TransactionRollbackException with the specified detail message.
     * This exception is used to indicate that a transaction has been rolled back due
     * to conflicts or constraints encountered during its execution.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public TransactionRollbackException(String message) {
        super(message);
        this.errorCode = HttpStatus.CONFLICT.value();
        this.failedOperation = null;
    }
}
