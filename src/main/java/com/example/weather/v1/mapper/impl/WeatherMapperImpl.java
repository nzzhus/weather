package com.example.weather.v1.mapper.impl;

import com.example.weather.entity.Weather;
import com.example.weather.v1.mapper.WeatherMapper;
import com.example.weather.v1.model.WeatherDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WeatherMapperImpl implements WeatherMapper {

    @Override
    public WeatherDto toDto(Weather weather) {
        Objects.requireNonNull(weather);
        return new WeatherDto(weather.city(), weather.temperature(), weather.unit(), weather.date(), weather.weather());
    }
}
