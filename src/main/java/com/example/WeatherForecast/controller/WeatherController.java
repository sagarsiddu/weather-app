package com.example.WeatherForecast.controller;

import com.example.WeatherForecast.model.WeatherResponse;
import com.example.WeatherForecast.service.WeatherService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
        WeatherResponse response = new WeatherResponse(city, 20.0);
        return ResponseEntity.ok(response);
    }




}
