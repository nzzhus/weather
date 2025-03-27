package com.example.weather.v1.service.impl;

import com.example.weather.entity.Weather;
import com.example.weather.exception.NotFoundException;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.support.WeatherSupport;
import com.example.weather.v1.mapper.WeatherMapper;
import com.example.weather.v1.model.WeatherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.weather.exception.ErrorCode.CITY_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceImplTest {

    @Mock
    private WeatherSupport weatherSupport;
    @Mock
    private WeatherMapper weatherMapper;
    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    @DisplayName("When runtime exception thrown from repository, the exception should throw")
    void getWeatherRuntimeException() {
        when(weatherSupport.getWeatherByCity(anyString())).thenThrow(new RuntimeException("Test"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> weatherService.getWeather("any"));
        assertEquals("Test", ex.getMessage());
    }

    @Test
    @DisplayName("When no weather from repository, should throw not found exception")
    void getWeatherNotFound() {
        NotFoundException notFound = assertThrows(NotFoundException.class, () -> weatherService.getWeather("any"));
        assertEquals(CITY_NOT_FOUND, notFound.getCode());
        assertEquals("any", notFound.getDetail());
        verify(weatherSupport).getWeatherByCity("any");
        verify(weatherMapper, never()).toDto(any(Weather.class));
    }

    @Test
    @DisplayName("When city's weather found, should return")
    void getWeather() {
        Weather entity = Mockito.mock(Weather.class);
        WeatherDto dto = Mockito.mock(WeatherDto.class);
        when(weatherSupport.getWeatherByCity("any")).thenReturn(Optional.of(entity));
        when(weatherMapper.toDto(entity)).thenReturn(dto);

        WeatherDto anyWeather = weatherService.getWeather("any");

        assertEquals(dto, anyWeather);
        verify(weatherSupport).getWeatherByCity("any");
        verify(weatherMapper).toDto(entity);
    }

}
