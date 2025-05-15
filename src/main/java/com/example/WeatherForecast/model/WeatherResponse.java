package com.example.WeatherForecast.model;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Data
//@Entity
//@Table(name = "weather_responses")
//@NoArgsConstructor
public class WeatherResponse {
    //@Id
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
