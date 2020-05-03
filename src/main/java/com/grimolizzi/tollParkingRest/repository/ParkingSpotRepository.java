package com.grimolizzi.tollParkingRest.repository;

import com.grimolizzi.tollParkingRest.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.model.PossibleCarType;
import org.springframework.data.repository.CrudRepository;

public interface ParkingSpotRepository extends CrudRepository<ParkingSpot, Long> {

    Iterable<ParkingSpot> findByTollParkingId(long tollParkingId);

    ParkingSpot findByTollParkingIdAndLicensePlate(
            long tollParkingId,
            String licensePlate);

    Iterable<ParkingSpot> findByTollParkingIdAndInUseAndPossibleCarType(
            long tollParkingId,
            boolean inUse,
            PossibleCarType possibleCarType);
}
