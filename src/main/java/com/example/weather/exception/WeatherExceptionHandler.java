package com.example.weather.exception;

import com.example.weather.exception.dto.ErrorDto;
import com.example.weather.provider.WeatherProviderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.weather.exception.ErrorCode.CITY_NOT_FOUND;

@Slf4j
@RestControllerAdvice
/**
 * Global exception handler for controllers
 */
public class WeatherExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND.value(), exception.getCode().name(), exception.getDetail(), exception.getCode().getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WeatherProviderException.class})
    public ResponseEntity<ErrorDto> handClientException(WeatherProviderException exception) {

        HttpStatusCode statusCode = exception.getStatusCode();
        if (statusCode.isSameCodeAs(HttpStatus.NOT_FOUND) ) {
            return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND.value(), CITY_NOT_FOUND.name(), exception.getDetail(), exception.getMessage()), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL_SERVICE_ERROR.name(), exception.getDetail(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorDto> handleException(Throwable t) {
        log.error("Unexpected errors", t);
        return new ResponseEntity<>(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL_SERVICE_ERROR.name(), null, ErrorCode.INTERNAL_SERVICE_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
