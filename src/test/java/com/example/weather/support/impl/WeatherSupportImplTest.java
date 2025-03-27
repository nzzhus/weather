package com.example.weather.support.impl;

import com.example.weather.entity.Weather;
import com.example.weather.provider.WeatherProvider;
import com.example.weather.provider.WeatherProviderException;
import com.example.weather.provider.model.WeatherResponse;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.v1.model.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherSupportImplTest {
    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherProvider weatherProvider;

    @InjectMocks
    private WeatherSupportImpl weatherSupport;

    @Test
    @DisplayName("Found from repository")
    void getWeatherByCityFromRepository() {
        Weather weather = Mockito.mock(Weather.class);
        when(weatherRepository.findWeatherByCity("any")).thenReturn(Optional.of(weather));
        Optional<Weather> weatherByCity = weatherSupport.getWeatherByCity("any");
        assertTrue(weatherByCity.isPresent());
        assertEquals(weather, weatherByCity.get());
        verify(weatherRepository).findWeatherByCity("any");
        verify(weatherProvider, never()).fetchWeather(anyString());
    }

    @Test
    @DisplayName("Not in store, so will get from provider")
    void getWeatherFromProvider() {
        when(weatherRepository.findWeatherByCity("any")).thenReturn(Optional.empty());
        WeatherResponse response = new WeatherResponse("11", "23/03/2025", "Cloudy", "any");
        when(weatherProvider.fetchWeather("any")).thenReturn(response);
        when(weatherRepository.save(any(Weather.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Optional<Weather> weatherByCity = weatherSupport.getWeatherByCity("any");

        verify(weatherRepository).findWeatherByCity("any");
        ArgumentCaptor<Weather> toSave = ArgumentCaptor.forClass(Weather.class);
        verify(weatherRepository).save(toSave.capture());
        verify(weatherProvider).fetchWeather("any");

        assertTrue(weatherByCity.isPresent());
        assertEquals("any", weatherByCity.get().city());
        assertEquals(Unit.C, weatherByCity.get().unit());
        assertEquals("11", weatherByCity.get().temperature());
        assertEquals("Cloudy", weatherByCity.get().weather());
        assertEquals(LocalDate.of(2025, 3, 23), weatherByCity.get().date());

        Weather savedWeather = toSave.getValue();
        assertNull(savedWeather.id());
        assertEquals("any", savedWeather.city());
        assertEquals(Unit.C, savedWeather.unit());
        assertEquals("11", savedWeather.temperature());
        assertEquals("Cloudy", savedWeather.weather());
        assertEquals(LocalDate.of(2025, 3, 23), savedWeather.date());
    }

    @Test
    @DisplayName("Call provider failed")
    void getWeatherByCityCallProviderFailed() {
        when(weatherRepository.findWeatherByCity("any")).thenReturn(Optional.empty());
        when(weatherProvider.fetchWeather("any")).thenThrow(new WeatherProviderException(HttpStatus.INTERNAL_SERVER_ERROR, "any", "Call provider failed"));
        WeatherProviderException ex = assertThrows(WeatherProviderException.class, () -> weatherSupport.getWeatherByCity("any"));
        verify(weatherRepository).findWeatherByCity("any");
        verify(weatherRepository, never()).save(any(Weather.class));
        verify(weatherProvider).fetchWeather("any");
    }
}
