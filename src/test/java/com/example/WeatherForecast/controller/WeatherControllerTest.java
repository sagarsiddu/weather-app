package com.example.WeatherForecast.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WeatherControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void endpointShouldExist() throws Exception {
        mockMvc.perform(get("/api/weather")
                        .param("city", "anyCity"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRequireCityParameter() throws Exception {
        mockMvc.perform(get("/api/weather"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnJsonResponse() throws Exception {
        mockMvc.perform(get("/api/weather")
                        .param("city", "anyCity"))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void shouldReturnOkStatus() throws Exception {
        mockMvc.perform(get("/api/weather?city=London"))
                .andExpect(status().isOk());
    }

}