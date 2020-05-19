package com.grimolizzi.tollParkingRest.errorHandling;

import com.grimolizzi.tollParkingRest.errorHandling.exceptions.CarNotFoundException;
import com.grimolizzi.tollParkingRest.errorHandling.exceptions.NoAvailableParkingSpotException;
import com.grimolizzi.tollParkingRest.errorHandling.exceptions.TollParkingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalAdvice {

    @ResponseBody
    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String carNotFoundHandler(CarNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NoAvailableParkingSpotException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String noAvailableParkingSpotHandler(NoAvailableParkingSpotException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TollParkingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tollParkingNotFoundHandler(TollParkingNotFoundException ex) {
        return ex.getMessage();
    }
}
