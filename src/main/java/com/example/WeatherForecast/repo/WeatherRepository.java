package com.example.WeatherForecast.repo;

import com.example.WeatherForecast.model.WeatherResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherResponse, Long> {

}
