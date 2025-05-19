package com.example.WeatherForecast.service;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Service
//public class WeatherService {
//
//    @Value("${weather.api.key}")
//    private String apiKey;
//
//    private final RestTemplate restTemplate;
//
//    public WeatherService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> getWeatherData(String city) {
//        String url = String.format(
//                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
//                city, apiKey
//        );
//        return restTemplate.getForObject(url, Map.class);
//    }
//}
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;
    private final String units;

    public WeatherService(
            RestTemplate restTemplate,
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.url}") String apiUrl,
            @Value("${weather.api.units}") String units) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.units = units;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getWeatherData(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=%s", apiUrl, city, apiKey, units);
        return restTemplate.getForObject(url, Map.class);
    }
}
