package tn.esprit.user_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String error,        // Error type (e.g., "Bad Request")
        String message,      // Detailed message
        int status           // HTTP status code
) {
    public ErrorResponse(String error, String message, int status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }
}