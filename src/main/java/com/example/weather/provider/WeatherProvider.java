package com.example.weather.provider;

import com.example.weather.provider.model.WeatherResponse;

public interface WeatherProvider {
    /**
     * Fetch weather details from a provider
     * @param city the given city
     * @return The {@link WeatherResponse}
     */
    WeatherResponse fetchWeather(String city);
}
