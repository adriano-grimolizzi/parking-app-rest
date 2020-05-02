package com.grimolizzi.tollParkingRest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class TollParking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tollParking")
    @JsonIgnore
    private List<ParkingSpot> parkingSpotList;

    private String code;
    private String name;

    public void setName(String s) {
        this.name = s;
    }
}
