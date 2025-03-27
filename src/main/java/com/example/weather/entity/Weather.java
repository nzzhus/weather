package com.example.weather.entity;

import com.example.weather.v1.model.Unit;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder
/**
 * Represent a row data in weather table.
 */
public record Weather(Long id, String city, String temperature, Unit unit, LocalDate date, String weather) {
    public Weather{
        Objects.requireNonNull(city);
        Objects.requireNonNull(temperature);
        Objects.requireNonNull(unit);
        Objects.requireNonNull(date);
        Objects.requireNonNull(weather);
    }
}
