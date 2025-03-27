package com.example.weather.provider;


import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class WeatherProviderException extends RuntimeException{
    private final String detail;
    private final HttpStatusCode statusCode;

    public WeatherProviderException(HttpStatusCode statusCode, String detail, String message) {
        super(message);
        this.statusCode = statusCode;
        this.detail = detail;
    }
}
