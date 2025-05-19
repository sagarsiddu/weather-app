package com.example.WeatherForecast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from React's development server
        registry.addMapping("/**").allowedOrigins("http://localhost:3000")  // React dev server URL
                .allowedMethods("GET", "POST", "PUT").allowedHeaders("*");
    }
}
