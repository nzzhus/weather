package com.example.weather.v1.controller;

import com.example.weather.v1.model.WeatherDto;

public interface WeatherController {
    WeatherDto getWeather(String city);
}
