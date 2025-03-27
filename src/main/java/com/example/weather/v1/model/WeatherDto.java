package com.example.weather.v1.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder
public record WeatherDto(String city, String temperature, Unit unit, LocalDate date, String weather) {

    public WeatherDto{
        Objects.requireNonNull(city);
        Objects.requireNonNull(temperature);
        Objects.requireNonNull(unit);
        Objects.requireNonNull(date);
        Objects.requireNonNull(weather);
    }

}
