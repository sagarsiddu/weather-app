package com.example.WeatherForecast.service;

import com.example.WeatherForecast.model.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnResponseObject() {
        WeatherResponse response = weatherService.getWeatherData("anyCity");
        assertNotNull(response);
    }

    @Test
    void shouldNotAcceptEmptyCity() {
        assertThrows(IllegalArgumentException.class, () -> weatherService.getWeatherData(""));
    }

    @Test
    void shouldNotAcceptNullCity() {
        assertThrows(IllegalArgumentException.class, () -> weatherService.getWeatherData(null));
    }

    @Test
    void responseObjectShouldHaveRequiredFields() {
        WeatherResponse response = weatherService.getWeatherData("anyCity");
        assertNull(response.getCity());
        assertNotNull(response.getTemperature());
    }
}
