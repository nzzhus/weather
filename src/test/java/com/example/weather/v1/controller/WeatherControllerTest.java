package com.example.weather.v1.controller;

import com.example.weather.exception.ErrorCode;
import com.example.weather.exception.NotFoundException;
import com.example.weather.v1.model.Unit;
import com.example.weather.v1.model.WeatherDto;
import com.example.weather.v1.service.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean private WeatherService weatherService;

    @Test
    @DisplayName("Successfully get city's weather")
    void getWeatherSuccess() throws Exception {
        WeatherDto weatherDto = WeatherDto.builder()
                .city("any")
                .unit(Unit.C)
                .temperature("20")
                .weather("Sunny")
                .date(LocalDate.of(2025, 3, 28))
                .build();
        when(weatherService.getWeather("any")).thenReturn(weatherDto);

        mockMvc.perform(get("/api/v1/weather?city=any").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("any"))
                .andExpect(jsonPath("$.unit").value("C"))
                .andExpect(jsonPath("$.temperature").value("20"))
                .andExpect(jsonPath("$.weather").value("Sunny"))
                .andExpect(jsonPath("$.date").value("2025-03-28"))
                ;
    }

    @Test
    @DisplayName("City not found")
    void getWeatherNotFound() throws Exception {
        when(weatherService.getWeather("any")).thenThrow(new NotFoundException(ErrorCode.CITY_NOT_FOUND, "any"));

        mockMvc.perform(get("/api/v1/weather?city=any").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.error_code").value("CITY_NOT_FOUND"))
                .andExpect(jsonPath("$.detail").value("any"))
                .andExpect(jsonPath("$.error_message").value("City not found"))
        ;
    }

    @Test
    @DisplayName("Internal Server Error")
    void getWeatherInternalServerError() throws Exception {
        when(weatherService.getWeather("any")).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/weather?city=any").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.error_code").value(ErrorCode.INTERNAL_SERVICE_ERROR.name()))
                .andExpect(jsonPath("$.detail").doesNotExist())
                .andExpect(jsonPath("$.error_message").value(ErrorCode.INTERNAL_SERVICE_ERROR.getMessage()))
        ;
    }


}
