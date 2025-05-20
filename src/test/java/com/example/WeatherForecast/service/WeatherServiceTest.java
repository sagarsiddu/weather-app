package com.example.WeatherForecast.service;

import com.example.WeatherForecast.exception.WeatherServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    private final String API_KEY = "test-api-key";

    @Test
    void getWeatherByCity_ValidCity_ReturnsWeatherData() {
        // Arrange
        String city = "London";
        ReflectionTestUtils.setField(weatherService, "apiKey", API_KEY);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("city", new HashMap<String, Object>() {{
            put("name", "London");
        }});

        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, API_KEY
        );

        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class)))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<Map<String, Object>> response = weatherService.getWeatherByCity(city);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getWeatherByCity_NullCity_ThrowsException() {
        // Act & Assert
        assertThrows(WeatherServiceException.class, () ->
                weatherService.getWeatherByCity(null)
        );
    }

    @Test
    void getWeatherByCity_EmptyCity_ThrowsException() {
        // Act & Assert
        assertThrows(WeatherServiceException.class, () ->
                weatherService.getWeatherByCity("  ")
        );
    }

    @Test
    void getWeatherByCity_ApiReturnsNull_ThrowsException() {
        // Arrange
        String city = "London";
        ReflectionTestUtils.setField(weatherService, "apiKey", API_KEY);

        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, API_KEY
        );

        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class)))
                .thenReturn(null);

        // Act & Assert
        WeatherServiceException exception = assertThrows(WeatherServiceException.class, () ->
                weatherService.getWeatherByCity(city)
        );
        assertEquals("No weather data received for city: London", exception.getMessage());
    }

    @Test
    void getWeatherByCity_ApiThrowsException_ThrowsWeatherServiceException() {
        // Arrange
        String city = "London";
        ReflectionTestUtils.setField(weatherService, "apiKey", API_KEY);

        when(restTemplate.getForObject(anyString(), eq(Map.class)))
                .thenThrow(new RestClientException("API Error"));

        // Act & Assert
        WeatherServiceException exception = assertThrows(WeatherServiceException.class, () ->
                weatherService.getWeatherByCity(city)
        );
        assertTrue(exception.getMessage().contains("Error fetching weather data for city: London"));
    }
}