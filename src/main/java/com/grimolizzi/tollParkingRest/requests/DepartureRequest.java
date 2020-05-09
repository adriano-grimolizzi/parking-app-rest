package com.grimolizzi.tollParkingRest.requests;

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

    public DepartureRequest(
            String tollParkingCode,
            String carLicensePlate) {
        this.tollParkingCode = tollParkingCode;
        this.carLicensePlate = carLicensePlate;
        this.departureDate = new Date();
    }
}
