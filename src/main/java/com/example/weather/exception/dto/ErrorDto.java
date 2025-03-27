package com.example.weather.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDto(int code, @JsonProperty("error_code") String errorCode, String detail, @JsonProperty("error_message") String errorMessage) {
}
