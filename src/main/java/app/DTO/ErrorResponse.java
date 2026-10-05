package app.DTO;

public record ErrorResponse (
        int status,
        String message
){}
