package com.example.WeatherForecast.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.WeatherForecast.service.WeatherService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WeatherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();

        // Setup default mock response
        Map<String, Object> weatherData = new HashMap<>();
        weatherData.put("temperature", 20);
        when(weatherService.getWeatherByCity(anyString()))
                .thenReturn(ResponseEntity.ok(weatherData));
    }

    @Test
    void endpointShouldExist() throws Exception {
        mockMvc.perform(get("/api/weather/anyCity"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRequireCityParameter() throws Exception {
        mockMvc.perform(get("/api/weather/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnJsonResponse() throws Exception {
        mockMvc.perform(get("/api/weather/anyCity"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnOkStatus() throws Exception {
        mockMvc.perform(get("/api/weather/London"))
                .andExpect(status().isOk());
    }
}