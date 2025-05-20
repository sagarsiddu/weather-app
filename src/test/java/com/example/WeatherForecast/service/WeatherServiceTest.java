package com.example.WeatherForecast.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void getWeatherByCity_ValidCity_ReturnsWeatherData() {
        // Arrange
        String city = "London";
        String apiKey = "test-api-key";
        ReflectionTestUtils.setField(weatherService, "apiKey", apiKey);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("city", new HashMap<String, Object>() {{
            put("name", "London");
        }});

        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, apiKey
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
    void getWeatherByCity_EmptyResponse_ReturnsEmptyMap() {
        // Arrange
        String city = "London";
        String apiKey = "test-api-key";
        ReflectionTestUtils.setField(weatherService, "apiKey", apiKey);

        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, apiKey
        );

        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class)))
                .thenReturn(new HashMap<>());

        // Act
        ResponseEntity<Map<String, Object>> response = weatherService.getWeatherByCity(city);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getWeatherByCity_NullResponse_ReturnsOkWithNull() {
        // Arrange
        String city = "London";
        String apiKey = "test-api-key";
        ReflectionTestUtils.setField(weatherService, "apiKey", apiKey);

        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, apiKey
        );

        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class)))
                .thenReturn(null);

        // Act
        ResponseEntity<Map<String, Object>> response = weatherService.getWeatherByCity(city);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNull(response.getBody());
    }

    @Test
    void getWeatherByCity_ApiError_ThrowsException() {
        // Arrange
        String city = "London";
        String apiKey = "test-api-key";
        ReflectionTestUtils.setField(weatherService, "apiKey", apiKey);

        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, apiKey
        );

        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(
                        HttpStatus.NOT_FOUND,
                        "Not Found",
                        null,
                        null,
                        null
                ));

        // Act & Assert
        assertThrows(HttpClientErrorException.class, () ->
                weatherService.getWeatherByCity(city)
        );
    }

    @Test
    void getWeatherByCity_UrlConstruction_UsesCorrectFormat() {
        // Arrange
        String city = "New York";
        String apiKey = "test-api-key";
        ReflectionTestUtils.setField(weatherService, "apiKey", apiKey);

        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, apiKey
        );

        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class)))
                .thenReturn(new HashMap<>());

        // Act
        weatherService.getWeatherByCity(city);

        // Assert is handled by verify in the mock setup - if URL doesn't match, test will fail
    }
}