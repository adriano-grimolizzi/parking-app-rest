package com.grimolizzi.tollParkingRest.spots.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BillingReceipt {
    private Date arrivalDate;
    private Date departureDate;
    private long hoursSpentInParkingSpot;
    private int hourlyRate;
    private int fixedAmount;
    private long amountDue;
}
