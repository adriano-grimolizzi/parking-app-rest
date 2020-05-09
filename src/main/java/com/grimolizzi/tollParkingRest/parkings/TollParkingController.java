package com.grimolizzi.tollParkingRest.parkings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/toll-parkings")
public class TollParkingController {

    private TollParkingRepository repository;

    @Autowired
    public TollParkingController(TollParkingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<TollParking> findAll() {
        return this.repository.findAll();
    }

    @PostMapping
    public void save(@RequestBody TollParking request) {
        this.repository.save(new TollParking(request.getCode(), request.getName()));
    }
}
