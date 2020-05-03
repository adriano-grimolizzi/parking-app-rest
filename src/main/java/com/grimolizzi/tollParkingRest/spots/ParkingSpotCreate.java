package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.spots.PossibleCarType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingSpotCreate {

    private String code;
    private String tollParkingCode;
    private PossibleCarType possibleCarType;
}
