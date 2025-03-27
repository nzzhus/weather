package com.example.weather.v1.service.impl;

import com.example.weather.entity.Weather;
import com.example.weather.exception.ErrorCode;
import com.example.weather.exception.NotFoundException;
import com.example.weather.provider.WeatherProvider;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.support.WeatherSupport;
import com.example.weather.v1.mapper.WeatherMapper;
import com.example.weather.v1.model.WeatherDto;
import com.example.weather.v1.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherMapper weatherMapper;
    private final WeatherSupport weatherSupport;

    @Override
    public WeatherDto getWeather(String city) {
        Optional<Weather> weatherByCity = weatherSupport.getWeatherByCity(city);
        if(weatherByCity.isPresent()) {
            return weatherMapper.toDto(weatherByCity.get());
        }
        throw new NotFoundException(ErrorCode.CITY_NOT_FOUND, city);
    }
}
