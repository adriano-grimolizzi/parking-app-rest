package com.grimolizzi.tollParkingRest.spots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotCreate {

    private String code;
    private String tollParkingCode;
    private PossibleCarType possibleCarType;
}
