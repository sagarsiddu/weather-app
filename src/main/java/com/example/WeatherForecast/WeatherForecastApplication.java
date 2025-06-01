package com.example.WeatherForecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.WeatherForecast")

public class WeatherForecastApplication {

    public static void main(String[] args) {

        SpringApplication.run(WeatherForecastApplication.class, args);
        System.out.println("Open weather API");
    }

}
