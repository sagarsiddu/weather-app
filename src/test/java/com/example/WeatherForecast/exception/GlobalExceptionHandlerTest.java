package com.example.WeatherForecast.exception;

import com.example.WeatherForecast.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/weather");
    }

    @Test
    void handleWeatherServiceException() {
        // Arrange
        WeatherServiceException ex = new WeatherServiceException("Test error");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleWeatherServiceException(ex, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test error", response.getBody().getMessage());
        assertEquals("/api/weather", response.getBody().getPath());
        assertEquals("WEATHER_SERVICE_ERROR", response.getBody().getErrorCode());
    }

    @Test
    void handleHttpClientErrorException() {
        // Arrange
        HttpClientErrorException ex = HttpClientErrorException.create(
                HttpStatus.NOT_FOUND,
                "Not Found",
                null,
                null,
                null
        );

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleHttpClientErrorException(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error fetching weather data: Not Found", response.getBody().getMessage());
        assertEquals("EXTERNAL_API_ERROR", response.getBody().getErrorCode());
    }

    @Test
    void handleGenericException() {
        // Arrange
        Exception ex = new RuntimeException("Unexpected error");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericException(ex, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getErrorCode());
    }
}