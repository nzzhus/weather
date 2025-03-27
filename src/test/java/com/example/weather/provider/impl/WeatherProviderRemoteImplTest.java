package com.example.weather.provider.impl;

import com.example.weather.config.AppConfig;
import com.example.weather.config.WeatherProviderConfig;
import com.example.weather.provider.WeatherProvider;
import com.example.weather.provider.WeatherProviderException;
import com.example.weather.provider.model.WeatherResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest({AppConfig.class, WeatherProviderRemoteImpl.class})
public class WeatherProviderRemoteImplTest {

    @Autowired
    private WeatherProviderConfig weatherProviderConfig;
    @Autowired
    private WeatherProvider weatherProvider;
    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Test
    @DisplayName("Fetch correct weather details")
    void fetchWeatherByCity() {
        mockRestServiceServer
                .expect(
                        requestTo(weatherProviderConfig.getBase()+weatherProviderConfig.getWeather())
                )
                .andRespond(
                        withSuccess(
                                """
                                        {
                                            "temp":"12",
                                            "city":"Auckland",
                                            "detail": "Sunny",
                                            "date": "28/03/2025"
                                        }
                                        """,
                                MediaType.APPLICATION_JSON
                        )
                );

        WeatherResponse response = weatherProvider.fetchWeather("Auckland");
        assertEquals("Auckland", response.city());
        assertEquals("28/03/2025", response.date());
        assertEquals("Sunny", response.detail());
        assertEquals("12", response.temp());
    }

    @Test
    @DisplayName("Not found")
    void fetchWeatherByCityNotFound(){
        mockRestServiceServer
                .expect(
                        requestTo(weatherProviderConfig.getBase()+weatherProviderConfig.getWeather())
                )
                .andRespond(
                        withResourceNotFound()
                );

        WeatherProviderException ex = assertThrows(WeatherProviderException.class, () -> weatherProvider.fetchWeather("any"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("any", ex.getDetail());
        assertEquals("Call provider failed", ex.getMessage());
    }

}
