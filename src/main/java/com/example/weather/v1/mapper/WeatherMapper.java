package com.example.weather.v1.mapper;

import com.example.weather.entity.Weather;
import com.example.weather.v1.model.WeatherDto;

public interface WeatherMapper {

    /**
     * Create a {@link WeatherDto} from given {@link Weather} entity
     * @param weather The {@link Weather} entity
     * @return a {@link WeatherDto}
     */
    WeatherDto toDto(Weather weather);
}
