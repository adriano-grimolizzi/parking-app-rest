package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.spots.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.spots.model.PossibleCarType;
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
