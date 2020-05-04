package com.grimolizzi.tollParkingRest.parkings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grimolizzi.tollParkingRest.spots.model.ParkingSpot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TollParking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tollParking")
    @JsonIgnore
    private List<ParkingSpot> parkingSpotList;

    private String code;
    private String name;

    private int fixedAmount; // can be 0
    private int hourlyRate;

    public TollParking(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
