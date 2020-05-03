package com.grimolizzi.tollParkingRest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ArrivalRequest {

    private String tollParkingCode;
    private String carLicensePlate;
    private PossibleCarType possibleCarType;
    private Date arrivalDate;
}
