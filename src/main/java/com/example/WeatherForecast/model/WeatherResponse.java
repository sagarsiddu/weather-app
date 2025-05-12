package com.example.WeatherForecast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherResponse {
    private String city;
    private String description;
    private double temperature;
    private int humidity;

    public WeatherResponse(String city, String description, double temperature, int humidity) {
        this.city = city;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
