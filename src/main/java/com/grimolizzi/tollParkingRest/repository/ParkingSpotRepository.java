package com.grimolizzi.tollParkingRest.repository;

import com.grimolizzi.tollParkingRest.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.model.TollParking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSpotRepository extends CrudRepository<ParkingSpot, Long> {

    Iterable<ParkingSpot> findByTollParkingId(long tollParkingId);
}
