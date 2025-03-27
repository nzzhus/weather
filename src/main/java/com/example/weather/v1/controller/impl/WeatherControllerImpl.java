package com.example.weather.v1.controller.impl;


import com.example.weather.v1.controller.WeatherController;
import com.example.weather.v1.model.WeatherDto;
import com.example.weather.v1.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/weather", produces = APPLICATION_JSON_VALUE)
public class WeatherControllerImpl implements WeatherController {
    private final WeatherService weatherService;

    @Override
    @GetMapping()
    public WeatherDto getWeather(@RequestParam(required = true) String city) {
        return weatherService.getWeather(city);
    }
}
