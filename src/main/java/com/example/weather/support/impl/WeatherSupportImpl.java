package com.example.weather.support.impl;

import com.example.weather.entity.Weather;
import com.example.weather.provider.WeatherProvider;
import com.example.weather.provider.model.WeatherResponse;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.support.WeatherSupport;
import com.example.weather.v1.model.Unit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherSupportImpl implements WeatherSupport {
    private final WeatherRepository weatherRepository;
    private final WeatherProvider weatherProvider;

    /**
     * if cannot find the weather from db, try to get weather from provider and save to db
     * @param city the given city
     * @return {@link Optional}
     */
    @Override
    public Optional<Weather> getWeatherByCity(String city) {
        Optional<Weather> weatherByCity = weatherRepository.findWeatherByCity(city);
        if(weatherByCity.isEmpty()) {
            WeatherResponse weatherResponse = weatherProvider.fetchWeather(city);
            weatherByCity = Optional.ofNullable(weatherRepository.save(toWeather(weatherResponse)));
        }
        return weatherByCity;
    }

    private Weather toWeather(WeatherResponse weatherResponse) {
       return Weather.builder()
                .unit(Unit.C)
                .date(LocalDate.parse(weatherResponse.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .city(weatherResponse.city())
                .temperature(weatherResponse.temp())
                .weather(weatherResponse.detail())
                .build()
        ;
    }
}
