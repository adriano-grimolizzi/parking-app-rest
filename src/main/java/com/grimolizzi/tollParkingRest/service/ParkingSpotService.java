package com.grimolizzi.tollParkingRest.service;

import com.grimolizzi.tollParkingRest.model.AvailableSpotSearch;
import com.grimolizzi.tollParkingRest.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.model.ParkingSpotCreate;
import com.grimolizzi.tollParkingRest.model.TollParking;
import com.grimolizzi.tollParkingRest.repository.ParkingSpotRepository;
import com.grimolizzi.tollParkingRest.repository.TollParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        long tollParkingId = this.tollParkingRepository.findByCode(search.getTollParkingCode()).getId();
        return this.parkingSpotRepository.findByTollParkingIdAndInUseAndPossibleCarType(
                tollParkingId, false, search.getPossibleCarType());
    }

    public void saveParkingSpot(ParkingSpotCreate parkingSpotCreate) {
        TollParking tollParking = this.tollParkingRepository.findByCode(parkingSpotCreate.getTollParkingCode());
        ParkingSpot toBeCreated = new ParkingSpot(
                tollParking, parkingSpotCreate.getCode(), parkingSpotCreate.getPossibleCarType());
        this.parkingSpotRepository.save(toBeCreated);
    }
}

//    public void handleArrival(ArrivalRequest arrivalRequest) {
//        ParkingSpot searchObject = new ParkingSpot();
//        TollParking tollPArking = new TollParking();
//        tollPArking.setCode(arrivalRequest.getTollParkingCode());
//        searchObject.setTollParking(tollPArking);
//        searchObject.setPossibleCarType(arrivalRequest.getPossibleCarType());
//
//        Iterable<ParkingSpot> availableParkingSpots = this.retrieveAvailableByTollParkingCodeAndType(searchObject);
//        if (!isEmpty(availableParkingSpots)) {
//            ParkingSpot parkingSpot = availableParkingSpots.iterator().next();
//            this.parkingSpotRepository.delete(parkingSpot);
//            parkingSpot.setInUse(true);
//            this.parkingSpotRepository.save(parkingSpot);
//        } else {
//            // Throw Exception
//        }
//    }

//    private static boolean isEmpty(Iterable<ParkingSpot> iterable) {
//        return StreamSupport.stream(iterable.spliterator(), false).count() == 0;
//    }
//}
