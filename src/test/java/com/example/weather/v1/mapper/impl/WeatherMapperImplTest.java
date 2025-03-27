package com.example.weather.v1.mapper.impl;

import com.example.weather.entity.Weather;
import com.example.weather.v1.model.Unit;
import com.example.weather.v1.model.WeatherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeatherMapperImplTest {

    private WeatherMapperImpl weatherMapper;

    @BeforeEach
    void setUp() {
        weatherMapper = new WeatherMapperImpl();
    }

    @Test
    @DisplayName("Create DTO from Weather entity")
    void toDto() {
        WeatherDto weatherDto = weatherMapper.toDto(new Weather(1L, "Taupo", "24", Unit.C, LocalDate.of(2025, 6, 18), "Windy"));
        assertEquals("Taupo", weatherDto.city());
        assertEquals("24", weatherDto.temperature());
        assertEquals(Unit.C, weatherDto.unit());
        assertEquals(2025, weatherDto.date().getYear());
        assertEquals(6, weatherDto.date().getMonthValue());
        assertEquals(18, weatherDto.date().getDayOfMonth());
        assertEquals("Windy", weatherDto.weather());
    }

    @Test
    @DisplayName("NPE thrown when given weather is null")
    void toDtoWhenNullEntity() {
        assertThrows(NullPointerException.class, () -> weatherMapper.toDto(null));
    }
}
