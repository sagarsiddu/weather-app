package com.example.WeatherForecast.service;

import com.example.WeatherForecast.exception.WeatherServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Map<String, Object>> getWeatherByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new WeatherServiceException("City name cannot be null or empty");
        }

        try {
            String url = String.format(
                    "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                    city, apiKey
            );

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                throw new WeatherServiceException("No weather data received for city: " + city);
            }

            return ResponseEntity.ok(response);
        } catch (RestClientException e) {
            throw new WeatherServiceException("Error fetching weather data for city: " + city, e);
        }
    }
}