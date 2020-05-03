package com.grimolizzi.tollParkingRest.errorHandling;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String tollParkingCode, String licensePlate) {
        super("Could not find Car with license plate: " + licensePlate +
                " in Toll Parking with code: " + tollParkingCode);
    }
}
