package lk.travel.travellion.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * A centralized exception handling class annotated with {@code @RestControllerAdvice}
 * to handle various exceptions globally in a RESTful application.
 * This class intercepts exceptions and returns appropriate HTTP responses with structured error information.
 */
@RestControllerAdvice
public class GenericExceptionHandler {

    /**
     * Handles exceptions of type ResourceAlreadyExistException and generates an error response.
     *
     * @param ex the ResourceAlreadyExistException containing details about the error
     * @return a ResponseEntity containing an ApiResponse with the error message, HTTP status code,
     *         and any additional data provided in the exception
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceAlreadyExist(ResourceAlreadyExistException ex) {
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                ex.getAdditionalData()
        );
    }


    /**
     * Handles exceptions of type {@code ResourceNotFoundException}.
     * Converts the exception into a standardized {@code ApiResponse} with an HTTP 404 status code.
     *
     * @param ex the {@code ResourceNotFoundException} that triggered this handler
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} object with error details,
     *         including the exception message and any additional data associated with the exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                ex.getAdditionalData()
        );
    }

    /**
     * Handles exceptions of type InvalidInputException and returns an appropriate
     * response entity containing error details.
     *
     * @param ex the InvalidInputException instance containing error details and additional data
     * @return a ResponseEntity containing an ApiResponse object with error message,
     *         HTTP status code, and any additional data related to the invalid input
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidInput(InvalidInputException ex) {
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ex.getAdditionalData() != null ? ex.getAdditionalData() : "No additional data"
        );
    }

    /**
     * Handles validation errors that occur when method arguments fail validation checks.
     * This method processes {@code MethodArgumentNotValidException} and extracts the
     * validation error messages associated with the invalid fields.
     *
     * @param ex the exception containing validation error details
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} with the
     *         error messages and {@code HttpStatus.BAD_REQUEST} status code
     */
    // Generic handler for all Validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        String errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()) // Field name + message
                .collect(Collectors.joining(", "));

        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                errorMessages
        );
    }

    /**
     * Handles exceptions of type {@code NoResourceFoundException}.
     * Returns an appropriate error response with an error message, HTTP status code of 404 (Not Found),
     * and additional context about the requested resource path.
     *
     * @param ex the {@code NoResourceFoundException} thrown when a resource is not found
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} object with error details
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoResourceFound(NoResourceFoundException ex) {
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                "Requested path : " + ex.getResourcePath()
        );
    }

    /**
     * Handles {@link ConstraintViolationException} and constructs a response containing
     * detailed validation error messages.
     *
     * @param ex the {@link ConstraintViolationException} to be handled, which encapsulates
     *           violations caused by invalid input constraints
     * @return a {@link ResponseEntity} containing an {@link ApiResponse} with a
     *         BAD_REQUEST status, error message, and detailed validation errors
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(ConstraintViolationException ex) {

        String errorMessages = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));


        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                errorMessages
        );
    }

    /**
     * Handles exceptions of type {@code ForeignKeyConstraintViolationException} and creates a
     * response entity containing the error details.
     *
     * @param ex the exception object containing details about the foreign key constraint violation
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} object with the exception message,
     *         HTTP 409 (Conflict) status code, and additional data associated with the exception
     */
    @ExceptionHandler(ForeignKeyConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleForeignKeyConstraintViolationException(ForeignKeyConstraintViolationException ex) {
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                ex.getAdditionalData()
        );
    }

    /**
     * Handles exceptions of type {@link HttpMessageNotReadableException} that occur when
     * a request contains invalid JSON or an unrecognized field.
     *
     * @param ex the exception thrown when the JSON in the request is invalid or contains unrecognized fields
     * @return a {@link ResponseEntity} containing an {@link ApiResponse} with error details,
     *         including HTTP status code {@code 400 BAD_REQUEST}, the exception message,
     *         and a description identifying the unrecognized JSON field
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleJsonProcessingException(HttpMessageNotReadableException ex) {

        String invalidField = "Unknown field";
        String message = ex.getMessage();

        if (message != null && message.contains("Unrecognized field")) {
            int startIndex = message.indexOf("\"") + 1;
            int endIndex = message.indexOf("\"", startIndex);
            if (startIndex > 0 && endIndex > startIndex) {
                invalidField = message.substring(startIndex, endIndex); // Extract the field name
            }
        }

        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                "Invalid JSON or Request Format, Unrecognized field in request: " + invalidField
        );
    }

    /**
     * Handles exceptions of type {@code TransactionRollbackException} and generates an appropriate HTTP response.
     * This method constructs a response with error details when a transaction rollback occurs due to a failure
     * in a specific operation.
     *
     * @param ex the {@code TransactionRollbackException} exception containing details about the rollback and the failed operation
     * @return a {@code ResponseEntity} containing an {@code ApiResponse<String>} object with the error message,
     *         HTTP status {@code 424 FAILED_DEPENDENCY}, and additional information about the failed operation
     */
    @ExceptionHandler(TransactionRollbackException.class)
    public ResponseEntity<ApiResponse<String>> handleTransactionRollbackException(TransactionRollbackException ex) {

        String failedOperation = ex.getFailedOperation() != null ? ex.getFailedOperation() : "Unknown operation";
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.FAILED_DEPENDENCY,
                "Rollback triggered due to failure in : " + failedOperation
        );
    }

    /**
     * Handles {@link AuthenticationException} and constructs an error response.
     *
     * @param ex the exception that was thrown during authentication failure
     * @return a {@link ResponseEntity} containing an {@link ApiResponse} with error details,
     *         including the error message, HTTP status code (401 Unauthorized), and a generic error description
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                "Invalid Credentials"
        );
    }

    /**
     * Handles exceptions of type {@code UsernameNotFoundException} and returns a structured
     * error response in the form of an {@code ApiResponse}.
     *
     * @param ex the exception instance of {@code UsernameNotFoundException} to be handled
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} with the error message,
     *         HTTP status {@code UNAUTHORIZED}, and additional information indicating "User not found"
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFound(UsernameNotFoundException ex) {
        return ApiResponse.errorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                "User not found"
        );
    }

    /**
     * Handles any common exceptions that are not specifically handled by other exception handlers
     * within the application. This method provides a standardized error response for unexpected
     * server errors, including the error message and a recommendation to contact the administrator.
     *
     * @param ex the exception that was thrown during the request processing
     * @return a ResponseEntity containing an ApiResponse with the error message, HTTP status code
     *         of INTERNAL_SERVER_ERROR, and details regarding the exception
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        return ApiResponse.errorResponse(
                "An Unexpected Server Error Occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Please Contact Site Administrator : " + ex.getMessage()
        );
    }
}
