package com.example.weather.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Configuration
public class AppConfig {

    private final WeatherProviderConfig weatherProviderConfig;

    @Bean
    public RestClient providerClient(RestClient.Builder builder) {
        return builder
                .baseUrl(weatherProviderConfig.getBase())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }
}
