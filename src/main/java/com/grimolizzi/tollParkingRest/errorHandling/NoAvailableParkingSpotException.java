package com.grimolizzi.tollParkingRest.errorHandling;

import com.grimolizzi.tollParkingRest.spots.model.PossibleCarType;

public class NoAvailableParkingSpotException extends RuntimeException {

    public NoAvailableParkingSpotException(String code, PossibleCarType type) {
        super("Could not find available spots of type " + type + " for Toll Parking with code: " + code);
    }
}
