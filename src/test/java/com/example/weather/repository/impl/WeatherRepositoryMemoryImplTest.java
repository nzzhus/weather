package com.example.weather.repository.impl;

import com.example.weather.entity.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherRepositoryMemoryImplTest {

    private WeatherRepositoryMemoryImpl weatherRepository;

    @BeforeEach
    void setUp() {
        weatherRepository = new WeatherRepositoryMemoryImpl();
        weatherRepository.init();
    }

    @Test
    @DisplayName("When city is null, exception should be thrown")
    void findByNullCity() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> weatherRepository.findWeatherByCity(null));
        assertEquals("City was null", exception.getMessage());
    }

    @Test
    @DisplayName("Find by an existing city, should return the weather for the city")
    void findByCity() {
        Optional<Weather> weatherOpt = weatherRepository.findWeatherByCity("auckland");
        assertTrue(weatherOpt.isPresent());
        assertEquals(1L, weatherOpt.get().id());
    }

    @Test
    @DisplayName("Find by an existing city, not case sensitive")
    void findByCityNotCaseSensitive(){
        Optional<Weather> weatherOpt = weatherRepository.findWeatherByCity("auckLanD");
        assertTrue(weatherOpt.isPresent());
        assertEquals(1L, weatherOpt.get().id());
    }

    @Test
    @DisplayName("No city's weather")
    void findByCityNoCity() {
        Optional<Weather> weatherOpt = weatherRepository.findWeatherByCity("No");
        assertTrue(weatherOpt.isEmpty());
    }
}
