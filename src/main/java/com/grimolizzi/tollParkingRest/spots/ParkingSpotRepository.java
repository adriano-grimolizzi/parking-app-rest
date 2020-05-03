package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.spots.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.spots.model.PossibleCarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    Iterable<ParkingSpot> findByTollParkingId(long tollParkingId);

    Optional<ParkingSpot> findByTollParkingIdAndLicensePlate(
            long tollParkingId,
            String licensePlate);

    Optional<Iterable<ParkingSpot>> findByTollParkingIdAndInUseAndPossibleCarType(
            long tollParkingId,
            boolean inUse,
            PossibleCarType possibleCarType);
}
