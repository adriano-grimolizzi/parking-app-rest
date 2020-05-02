package com.grimolizzi.tollParkingRest.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "toll_parking_id", nullable = false)
    private TollParking tollParking;

    private String code;

    @Enumerated(EnumType.STRING)
    private PossibleCarType possibleCarType;

    private String licensePlate;

    private Date timeOfArrival;

    boolean inUse;
}
