package com.grimolizzi.tollParkingRest.errorHandling;

public class TollParkingNotFoundException extends RuntimeException {

    public TollParkingNotFoundException(String nameOrCode, String value) {
        super("Could not find Toll Parking with " + nameOrCode + ": " + value);
    }
}
