package com.example.WeatherForecast.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.annotation.TypeName;

import java.time.LocalDateTime;


@Data
@Entity
@TypeName("WeatherResponse")
@Table(name = "weather_responses")
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private Double temperature;
    private Double humidity;
    private String description;
    private LocalDateTime timestamp;

    public WeatherResponse(String city, String description, Double temperature, Double humidity, LocalDateTime timestamp) {
        this.city = city;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherResponse that)) return false;
        return id != null && id.equals(that.id);
    }


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

