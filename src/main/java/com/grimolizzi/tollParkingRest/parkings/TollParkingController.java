package com.grimolizzi.tollParkingRest.parkings;

import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.parkings.TollParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tollParkings")
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

    @PostMapping("/code/{code}/name/{name}")
    public void save(
            @PathVariable String code,
            @PathVariable String name) {
        this.repository.save(new TollParking(code, name));
    }
}
