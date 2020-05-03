package com.grimolizzi.tollParkingRest.service;

import com.grimolizzi.tollParkingRest.model.*;
import com.grimolizzi.tollParkingRest.repository.ParkingSpotRepository;
import com.grimolizzi.tollParkingRest.repository.TollParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class ParkingSpotService {

    private final TollParkingRepository tollParkingRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingSpotService(
            TollParkingRepository tollParkingRepository,
            ParkingSpotRepository parkingSpotRepository) {
        this.tollParkingRepository = tollParkingRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public Iterable<ParkingSpot> retrieveAllParkingSpots() {
        return this.parkingSpotRepository.findAll();
    }

    public Iterable<ParkingSpot> retrieveByTollParkingCode(String tollParkingCode) {
        TollParking tollParking = this.tollParkingRepository.findByCode(tollParkingCode);
        return this.retrieveByTollParkingId(tollParking);
    }

    public Iterable<ParkingSpot> retrieveByTollParkingName(String tollParkingName) {
        TollParking tollParking = this.tollParkingRepository.findByName(tollParkingName);
        return this.retrieveByTollParkingId(tollParking);
    }

    private Iterable<ParkingSpot> retrieveByTollParkingId(TollParking tollParking) {
        if (tollParking != null) {
            return this.parkingSpotRepository.findByTollParkingId(tollParking.getId());
        } else {
            return null;
        }
    }

    public Iterable<ParkingSpot> retrieveAvailableSpot(AvailableSpotSearch search) {
        TollParking tollParking = this.tollParkingRepository.findByCode(search.getTollParkingCode());
        if (tollParking != null) {
            return this.parkingSpotRepository.findByTollParkingIdAndInUseAndPossibleCarType(
                    tollParking.getId(), false, search.getPossibleCarType());
        } else {
            return null;
        }
    }

    public void saveParkingSpot(ParkingSpotCreate parkingSpotCreate) {
        TollParking tollParking = this.tollParkingRepository.findByCode(parkingSpotCreate.getTollParkingCode());
        ParkingSpot toBeCreated = new ParkingSpot(
                tollParking, parkingSpotCreate.getCode(), parkingSpotCreate.getPossibleCarType());
        this.parkingSpotRepository.save(toBeCreated);
    }

    public void handleArrival(ArrivalRequest arrivalRequest) {

        Iterable<ParkingSpot> availableSpots = this.retrieveAvailableSpot(
                new AvailableSpotSearch(arrivalRequest.getTollParkingCode(), arrivalRequest.getPossibleCarType()));

        if (!isEmpty(availableSpots)) {
            ParkingSpot parkingSpot = availableSpots.iterator().next();
            this.parkingSpotRepository.delete(parkingSpot);
            parkingSpot.setInUse(true);
            parkingSpot.setTimeOfArrival(arrivalRequest.getArrivalDate());
            parkingSpot.setLicensePlate(arrivalRequest.getCarLicensePlate());
            this.parkingSpotRepository.save(parkingSpot);
        } else {
            // Throw Exception
        }
    }

    private static boolean isEmpty(Iterable<ParkingSpot> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count() == 0;
    }

    public void handleDeparture(DepartureRequest departureRequest) {
        TollParking tollParking = this.tollParkingRepository.findByCode(departureRequest.getTollParkingCode());
        if (tollParking != null) {
            ParkingSpot parkingSpot = this.parkingSpotRepository.findByTollParkingIdAndLicensePlate(
                    tollParking.getId(), departureRequest.getCarLicensePlate());
            if (parkingSpot != null) {
                this.parkingSpotRepository.delete(parkingSpot);
                parkingSpot.setInUse(false);
                this.parkingSpotRepository.save(parkingSpot);
            }
        } else {
            // Throw Exception
        }
    }
}
