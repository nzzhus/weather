package com.example.weather.support;

import com.example.weather.entity.Weather;

import java.util.Optional;

public interface WeatherSupport {
    /**
     * Delegate repository to get weather by city
     * @param city the given city
     * @return {@link Optional<Weather>}
     */
    Optional<Weather> getWeatherByCity(String city);
}
