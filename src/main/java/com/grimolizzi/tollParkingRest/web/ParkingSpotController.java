package com.grimolizzi.tollParkingRest.web;

import com.grimolizzi.tollParkingRest.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.model.PossibleCarType;
import com.grimolizzi.tollParkingRest.model.TollParking;
import com.grimolizzi.tollParkingRest.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingSpots")
public class ParkingSpotController {

    private ParkingSpotService parkingSpotService;

    @Autowired
    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public Iterable<ParkingSpot> findAll() {
        return this.parkingSpotService.retrieveAllParkingSpots();
    }

    @GetMapping("/tollParkingName/{tollParkingName}")
    public Iterable<ParkingSpot> findByTollParkingName(@PathVariable String tollParkingName) {
        return this.parkingSpotService.retrieveByTollParkingCode(tollParkingName);
    }

    @GetMapping("/tollParkingCode/{tollParkingCode}")
    public Iterable<ParkingSpot> findByTollParkingCode(@PathVariable String tollParkingCode) {
        return this.parkingSpotService.retrieveByTollParkingCode(tollParkingCode);
    }

    @GetMapping("/inUse/tollParkingCode/{tollParkingCode}")
    public Iterable<ParkingSpot> findAvailable(@PathVariable String tollParkingCode) {
        return this.parkingSpotService.retrieveAvailableByTollParkingCode(tollParkingCode);
    }

    @GetMapping("/inUse/tollParkingCode/{tollParkingCode}/type/{possibleCarType}")
    public Iterable<ParkingSpot> findAvailable(
            @PathVariable String tollParkingCode,
            @PathVariable PossibleCarType possibleCarType) {
        ParkingSpot search = new ParkingSpot();
        TollParking tollParking = new TollParking();
        tollParking.setCode(tollParkingCode);
        search.setTollParking(tollParking);
        search.setPossibleCarType(possibleCarType);
        return this.parkingSpotService.retrieveAvailableByTollParkingCodeAndType(search);
    }

    @PostMapping("/code/{parkingSpotCode}/tollParkingCode/{tollParkingCode}")
    public void save(
            @PathVariable String parkingSpotCode,
            @PathVariable String tollParkingCode) {
        this.parkingSpotService.saveParkingSpot(parkingSpotCode, tollParkingCode);
    }
}
