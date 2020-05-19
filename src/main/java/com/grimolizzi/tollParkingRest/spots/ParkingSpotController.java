package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.model.*;
import com.grimolizzi.tollParkingRest.requests.ArrivalRequest;
import com.grimolizzi.tollParkingRest.requests.DepartureRequest;
import com.grimolizzi.tollParkingRest.requests.ParkingSpotCreate;
import com.grimolizzi.tollParkingRest.requests.AvailableSpotSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-spots")
public class ParkingSpotController {

    private ParkingSpotService parkingSpotService;

    @Autowired
    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public Iterable<ParkingSpot> find(
            @RequestParam(required = false) String tollParkingCode,
            @RequestParam(required = false) String tollParkingName) {
        if (tollParkingCode != null) {
            return this.parkingSpotService.retrieveByTollParkingCode(tollParkingCode);
        } else if (tollParkingName != null) {
            return this.parkingSpotService.retrieveByTollParkingName(tollParkingName);
        } else {
            return this.parkingSpotService.retrieveAllParkingSpots();
        }
    }

    @GetMapping("/available")
    public Iterable<ParkingSpot> findAvailable(
            @RequestParam String tollParkingCode,
            @RequestParam PossibleCarType possibleCarType) {
        return this.parkingSpotService.retrieveAvailableSpot(new AvailableSpotSearch(tollParkingCode, possibleCarType));
    }

    @PostMapping
    public void save(@RequestBody ParkingSpotCreate parkingSpotCreate) {
        this.parkingSpotService.saveParkingSpot(parkingSpotCreate);
    }

    @PostMapping("/handleArrival")
    public void handleArrival(@RequestBody ArrivalRequest request) {
        this.parkingSpotService.handleArrival(request);
    }

    @PostMapping("/handleDeparture")
    public BillingReceipt handleDeparture(@RequestBody DepartureRequest request) {
        return this.parkingSpotService.handleDeparture(request);
    }
}
