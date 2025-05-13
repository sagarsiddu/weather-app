package com.example.WeatherForecast.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "weather_response")
@Data
public class WeatherResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
