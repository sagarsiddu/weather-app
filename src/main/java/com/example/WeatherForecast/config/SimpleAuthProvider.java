package com.example.WeatherForecast.config;

import org.javers.spring.auditable.AuthorProvider;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthProvider implements AuthorProvider {
    @Override
    public String provide() {
        return "system";
    }
}