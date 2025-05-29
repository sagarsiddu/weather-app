package com.example.WeatherForecast.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    // Getters and setters
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;
    private List<String> errors;

    public ErrorResponse(String message, String path, String errorCode) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
        this.errors = new ArrayList<>();
    }

}