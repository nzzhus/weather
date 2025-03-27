package com.example.weather.repository;

import com.example.weather.entity.Weather;

import java.util.Optional;

public interface WeatherRepository {

    Optional<Weather> findWeatherByCity(String city);

    Weather save(Weather weather);

}
