package com.grimolizzi.tollParkingRest.repository;

import com.grimolizzi.tollParkingRest.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.model.TollParking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TollParkingRepository extends CrudRepository<TollParking, Long> {

    TollParking findByName(String name);

    TollParking findByCode(String code);
}
