package com.grimolizzi.tollParkingRest.requests;

import com.grimolizzi.tollParkingRest.model.PossibleCarType;
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
