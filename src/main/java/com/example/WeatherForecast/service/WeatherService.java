package com.example.WeatherForecast.service;

import com.example.WeatherForecast.model.WeatherResponse;
import com.example.WeatherForecast.repo.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }



}
