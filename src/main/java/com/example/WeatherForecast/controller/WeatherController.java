package com.example.WeatherForecast.controller;


import com.example.WeatherForecast.model.WeatherResponse;
import com.example.WeatherForecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private final WeatherService service;

    @Autowired
    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/{city}")
    public ResponseEntity<Map<String, Object>> getWeather(@PathVariable String city) {
        return service.getWeatherByCity(city);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(OAuth2AuthenticationToken authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Map<String, Object> userDetails = authentication.getPrincipal().getAttributes();
        return ResponseEntity.ok(userDetails);
    }

    // Save weather data to DB
    @PostMapping("/save")
    public WeatherResponse saveWeather(@RequestBody WeatherResponse weatherResponse) {
        return service.saveWeatherData(weatherResponse);
    }
}
