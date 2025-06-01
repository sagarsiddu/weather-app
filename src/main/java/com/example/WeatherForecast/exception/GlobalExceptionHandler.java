package com.example.WeatherForecast.exception;

import com.example.WeatherForecast.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<ErrorResponse> handleWeatherServiceException(
            WeatherServiceException ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                request.getRequestURI(),
                "WEATHER_SERVICE_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(
            HttpClientErrorException ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Error fetching weather data: " + ex.getStatusText(),
                request.getRequestURI(),
                "EXTERNAL_API_ERROR"
        );
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "An unexpected error occurred",
                request.getRequestURI(),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}