package com.example.weather.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private ErrorCode code;
    private String detail;

    public NotFoundException(ErrorCode code, String detail) {
        super(code.getMessage());
        this.code = code;
        this.detail = detail;
    }

}
