package com.example.weather.repository.impl;

import com.example.weather.entity.Weather;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.v1.model.Unit;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class WeatherRepositoryMemoryImpl implements WeatherRepository {

    private Map<String, Weather> weathers = new HashMap<>();

    @PostConstruct
    public void init() {
        weathers.put("auckland", new Weather(1L, "Auckland", "24", Unit.C, LocalDate.now(), "Windy"));
        weathers.put("hamilton", new Weather(1L, "Hamilton", "20", Unit.C, LocalDate.now(), "Cloudy"));
        weathers.put("taupo", new Weather(1L, "Taopu", "19", Unit.C, LocalDate.now(), "Sunny"));
    }

    @Override
    public Optional<Weather> findWeatherByCity(String city) {
        Objects.requireNonNull(city, "City was null");
        return Optional.ofNullable(weathers.get(city.trim().toLowerCase()));
    }
}
