package com.grimolizzi.tollParkingRest.web;

import com.grimolizzi.tollParkingRest.model.ParkingSpot;
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

    @GetMapping("/tollParkingCode/{tollParkingCode}")
    public Iterable<ParkingSpot> findByTollParkingCode(@PathVariable String tollParkingCode) {
        return this.parkingSpotService.retrieveByTollParkingCode(tollParkingCode);
    }

    @GetMapping("/tollParkingName/{tollParkingName}")
    public Iterable<ParkingSpot> findByTollParkingName(@PathVariable String tollParkingName) {
        return this.parkingSpotService.retrieveByTollParkingCode(tollParkingName);
    }

    @PostMapping("/code/{parkingSpotCode}/tollParkingCode/{tollParkingCode}")
    public void save(
            @PathVariable String parkingSpotCode,
            @PathVariable String tollParkingCode) {
        this.parkingSpotService.saveParkingSpot(parkingSpotCode, tollParkingCode);
    }
}
