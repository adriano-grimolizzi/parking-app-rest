package com.grimolizzi.tollParkingRest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingReceipt {
    private Date arrivalDate;
    private Date departureDate;
    private long hoursSpentInParkingSpot;
    private int hourlyRate;
    private int fixedAmount;
    private long amountDue;

    public BillingReceipt(
            Date arrivalDate,
            Date departureDate, 
            int hourlyRate, 
            int fixedAmount) {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.hourlyRate = hourlyRate;
        this.fixedAmount = fixedAmount;
        this.hoursSpentInParkingSpot = getHoursBetween(this.arrivalDate, this.departureDate);
        this.amountDue = this.hoursSpentInParkingSpot 
                * this.getHourlyRate()
                + this.getFixedAmount();
    }

    public static long getHoursBetween(Date arrivalDate, Date departureDate) {
        return (departureDate.getTime() - arrivalDate.getTime()) / 1000 / 3600;
    }
}
