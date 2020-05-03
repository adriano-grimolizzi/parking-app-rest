package com.grimolizzi.tollParkingRest.web;

import com.grimolizzi.tollParkingRest.model.*;
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
        return this.parkingSpotService.retrieveByTollParkingName(tollParkingName);
    }

    @GetMapping("/tollParkingCode/{tollParkingCode}")
    public Iterable<ParkingSpot> findByTollParkingCode(@PathVariable String tollParkingCode) {
        return this.parkingSpotService.retrieveByTollParkingCode(tollParkingCode);
    }

    @GetMapping("/available/tollParkingCode/{tollParkingCode}/possibleCarType/{possibleCarType}")
    public Iterable<ParkingSpot> findAvailable(
            @PathVariable String tollParkingCode,
            @PathVariable PossibleCarType possibleCarType) {
        return this.parkingSpotService.retrieveAvailableSpot(new AvailableSpotSearch(tollParkingCode, possibleCarType));
    }

    @PostMapping("/create")
    public void save(@RequestBody ParkingSpotCreate parkingSpotCreate) {
        this.parkingSpotService.saveParkingSpot(parkingSpotCreate);
    }

    @PostMapping("/handleArrival")
    public void handleArrival(@RequestBody ArrivalRequest request) {
        this.parkingSpotService.handleArrival(request);
    }

    @PostMapping("/handleDeparture")
    public void handleDeparture(@RequestBody DepartureRequest request) {
        this.parkingSpotService.handleDeparture(request);
    }
}
