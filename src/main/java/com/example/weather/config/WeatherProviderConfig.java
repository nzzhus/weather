package com.example.weather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("weather.provider")
public class WeatherProviderConfig {
    private String base;
    private String weather;
}
