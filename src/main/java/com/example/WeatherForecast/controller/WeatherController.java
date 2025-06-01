package com.example.WeatherForecast.controller;


import com.example.WeatherForecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private final WeatherService service;

    public WeatherController(WeatherService weatherService) {
        this.service = service;
    }

    @GetMapping("/{city}")
    public ResponseEntity<Map<String, Object>> getWeather(@PathVariable String city) {
        return service.getWeatherByCity(city);
    }
}

    // Save weather data to DB
    @PostMapping("/save")
    public WeatherResponse saveWeather(@RequestBody WeatherResponse weatherResponse) {
        return service.saveWeatherData(weatherResponse);
    }

}