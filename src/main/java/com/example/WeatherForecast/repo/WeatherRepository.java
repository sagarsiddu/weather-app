package com.example.WeatherForecast.repo;

import com.example.WeatherForecast.model.WeatherResponse;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface WeatherRepository extends JpaRepository<WeatherResponse, Long> {
    WeatherResponse findByCity(String city);

}
