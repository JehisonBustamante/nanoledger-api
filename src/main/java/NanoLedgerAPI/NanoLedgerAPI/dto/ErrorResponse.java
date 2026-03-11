package NanoLedgerAPI.NanoLedgerAPI.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String error;
    private LocalDateTime timestamp;
    private String details;

    public ErrorResponse(String error, LocalDateTime timestamp, String details) {
        this.error = error;
        this.timestamp = timestamp;
        this.details = details;
    }

    // Getters and Setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
