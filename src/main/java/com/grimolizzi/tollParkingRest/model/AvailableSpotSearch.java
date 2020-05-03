package com.grimolizzi.tollParkingRest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableSpotSearch {

    private String tollParkingCode;
    private PossibleCarType possibleCarType;
}
