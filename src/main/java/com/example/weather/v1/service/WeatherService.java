package com.example.weather.v1.service;

import com.example.weather.v1.model.WeatherDto;

public interface WeatherService {

    /**
     * Return a given city's current {@link WeatherDto}
     * @param city The given city
     * @return the {@link WeatherDto}
     */
    WeatherDto getWeather(String city);
}
