package com.grimolizzi.tollParkingRest.parkings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grimolizzi.tollParkingRest.spots.ParkingSpot;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
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
    private int fixedAmount;
    private int hourlyRate;

    public TollParking(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
