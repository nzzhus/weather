package com.example.weather.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CITY_NOT_FOUND("City not found"),
    INTERNAL_SERVICE_ERROR("Internal service error");
    private String message;
    ErrorCode(String message) {
        this.message = message;
    }
}
