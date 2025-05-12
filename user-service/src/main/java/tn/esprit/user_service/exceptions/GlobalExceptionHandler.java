package tn.esprit.user_service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tn.esprit.user_service.dto.ErrorResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntity(DuplicateEntityException ex) {
        return buildResponse(
                "Conflict",
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({BadCredentialsException.class, DisabledException.class})
    public ResponseEntity<ErrorResponse> handleAuthExceptions(RuntimeException ex) {
        return buildResponse(
                "Unauthorized",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler({StringIndexOutOfBoundsException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleInputExceptions(RuntimeException ex) {
        return buildResponse(
                "Bad Request",
                "Invalid input format: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInput(InvalidInputException ex) {
        return buildResponse(
                "Bad Request",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorResponse> handleAccountLocked(AccountLockedException ex) {
        return buildResponse(
                "Forbidden",
                ex.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return buildResponse(
                "Unauthorized",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("Unhandled RuntimeException: ", ex);
        return buildResponse(
                "Internal Server Error",
                ex.getMessage() != null ? ex.getMessage() : "Unexpected runtime error",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        Throwable rootCause = getRootCause(ex);
        log.error("Unhandled exception: ", rootCause);
        return buildResponse(
                "Internal Server Error",
                rootCause.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder messageBuilder = new StringBuilder("Validation error(s): ");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            messageBuilder.append(String.format("[%s: %s] ", fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return buildResponse(
                "Bad Request",
                messageBuilder.toString(),
                HttpStatus.BAD_REQUEST
        );
    }


    // Add this new handler
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return buildResponse(
                "Forbidden",
                "Access denied: " + ex.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    // Keep your existing handlers
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return buildResponse(
                "Forbidden",
                "You don't have permission to access this resource",
                HttpStatus.FORBIDDEN
        );
    }
    private ResponseEntity<ErrorResponse> buildResponse(String error, String message, HttpStatus status) {
        return new ResponseEntity<>(
                new ErrorResponse(error, message, status.value()),
                status
        );
    }

    private Throwable getRootCause(Throwable ex) {
        Throwable cause = ex;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }
}