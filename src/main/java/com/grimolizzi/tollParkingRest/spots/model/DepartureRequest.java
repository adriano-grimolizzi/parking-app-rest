package com.grimolizzi.tollParkingRest.spots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartureRequest {

    private String tollParkingCode;
    private String carLicensePlate;
    private Date departureDate;
}
