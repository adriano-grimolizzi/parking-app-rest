package com.grimolizzi.tollParkingRest.requests;

import com.grimolizzi.tollParkingRest.model.PossibleCarType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableSpotSearch {

    private String tollParkingCode;
    private PossibleCarType possibleCarType;
}
