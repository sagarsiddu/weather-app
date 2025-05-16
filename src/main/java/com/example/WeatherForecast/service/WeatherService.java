package com.example.WeatherForecast.service;

import com.example.WeatherForecast.model.WeatherResponse;
import com.example.WeatherForecast.repo.WeatherRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    public WeatherResponse getWeatherData(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }

        // For now, returning dummy data
        // In a real application, this would call an external weather API
        return new WeatherResponse();
    }


}
