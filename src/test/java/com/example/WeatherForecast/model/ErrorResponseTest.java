package com.example.WeatherForecast.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void createErrorResponse_ShouldSetAllFields() {
        // Arrange
        String message = "Test error message";
        String path = "/api/test";
        String errorCode = "TEST_ERROR";

        // Act
        ErrorResponse errorResponse = new ErrorResponse(message, path, errorCode);

        // Assert
        assertNotNull(errorResponse.getTimestamp());
        assertTrue(errorResponse.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(errorResponse.getTimestamp().isAfter(LocalDateTime.now().minusSeconds(1)));
        assertEquals(message, errorResponse.getMessage());
        assertEquals(path, errorResponse.getPath());
        assertEquals(errorCode, errorResponse.getErrorCode());
    }
}