package com.example.WeatherForecast.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    // Getters and setters
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;

    public ErrorResponse(String message, String path, String errorCode) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }

}