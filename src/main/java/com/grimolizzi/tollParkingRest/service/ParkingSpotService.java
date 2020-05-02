package com.grimolizzi.tollParkingRest.service;

import com.grimolizzi.tollParkingRest.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.model.TollParking;
import com.grimolizzi.tollParkingRest.repository.ParkingSpotRepository;
import com.grimolizzi.tollParkingRest.repository.TollParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    private TollParkingRepository tollParkingRepository;
    private ParkingSpotRepository parkingSpotRepository;

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
        return this.parkingSpotRepository.findByTollParkingId(tollParking.getId());
    }

    public Iterable<ParkingSpot> retrieveByTollParkingName(String tollParkingName) {
        TollParking tollParking = this.tollParkingRepository.findByName(tollParkingName);
        return this.parkingSpotRepository.findByTollParkingId(tollParking.getId());
    }

    public void saveParkingSpot(String parkingSpotCode, String tollParkingCode) {
        TollParking tollParking = this.tollParkingRepository.findByCode(tollParkingCode);
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setCode(parkingSpotCode);
        parkingSpot.setTollParking(tollParking);
        this.parkingSpotRepository.save(parkingSpot);
    }
}
