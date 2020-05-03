package com.grimolizzi.tollParkingRest.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoAvailableParkingSpotAdvice {

    @ResponseBody
    @ExceptionHandler(NoAvailableParkingSpotException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tollParkingNotFoundHandler(NoAvailableParkingSpotException ex) {
        return ex.getMessage();
    }
}
