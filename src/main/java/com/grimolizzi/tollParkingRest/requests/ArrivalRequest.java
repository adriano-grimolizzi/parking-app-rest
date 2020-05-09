package com.grimolizzi.tollParkingRest.requests;

import com.grimolizzi.tollParkingRest.model.PossibleCarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalRequest {

    private String tollParkingCode;
    private String carLicensePlate;
    private PossibleCarType possibleCarType;
    private Date arrivalDate;

    public ArrivalRequest(
            String tollParkingCode,
            String carLicensePlate,
            PossibleCarType possibleCarType) {
        this.tollParkingCode = tollParkingCode;
        this.carLicensePlate = carLicensePlate;
        this.possibleCarType = possibleCarType;
        this.arrivalDate = new Date();
    }
}
