package com.example.WeatherForecast.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    private final String testApiKey = "test-api-key";

    @BeforeEach
    void setup() {
        // Manually set the API key since @Value won't be injected in unit test
        ReflectionTestUtils.setField(weatherService, "apiKey", testApiKey);
    }

    @Test
    void testGetWeatherByCity() {
        // Given
        String city = "London";
        String expectedUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, testApiKey
        );

        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("city", "London");
        mockResponse.put("temp", 20);

        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class))).thenReturn(mockResponse);

        // When
        ResponseEntity<Map<String, Object>> response = weatherService.getWeatherByCity(city);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals("London", response.getBody().get("city"));
        assertEquals(20, response.getBody().get("temp"));
    }
}


