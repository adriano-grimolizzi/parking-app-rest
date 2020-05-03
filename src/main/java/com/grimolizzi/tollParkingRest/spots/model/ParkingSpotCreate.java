package com.grimolizzi.tollParkingRest.spots.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingSpotCreate {

    private String code;
    private String tollParkingCode;
    private PossibleCarType possibleCarType;
}
