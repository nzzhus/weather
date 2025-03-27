package com.example.weather.provider.impl;

import com.example.weather.config.WeatherProviderConfig;
import com.example.weather.provider.WeatherProvider;
import com.example.weather.provider.WeatherProviderException;
import com.example.weather.provider.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Slf4j
@Component
public class WeatherProviderRemoteImpl implements WeatherProvider {

    private final WeatherProviderConfig weatherProviderConfig;
    private final RestClient providerClient;

    @Override
    public WeatherResponse fetchWeather(String city) {
        log.debug("Call remote weather service {}{} for city {}", weatherProviderConfig.getBase(), weatherProviderConfig.getWeather(), city);
        return providerClient
                .get()
                .uri(weatherProviderConfig.getWeather())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        (req, resp ) -> {
                    throw new WeatherProviderException(resp.getStatusCode(), city, "Call provider failed");
                } )
                .body(WeatherResponse.class);
    }

}
