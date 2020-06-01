package com.grimolizzi.tollParkingRest.parkings;

import com.grimolizzi.tollParkingRest.spots.ParkingSpot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
@RequestMapping("/toll-parkings")
public class TollParkingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TollParkingController.class);

    private final TollParkingRepository repository;

    private static Function<Throwable, ResponseEntity<? extends List<TollParking>>> handleGetFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

    @Autowired
    public TollParkingController(TollParkingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public @ResponseBody
    CompletableFuture<ResponseEntity> findAll() {
        return this.repository.findAll()
                .<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetFailure);
    }

    @PostMapping
    public @ResponseBody ResponseEntity save(@RequestBody List<TollParking> request) {
        try {
            request.stream().forEach(tollParking -> {
                LOGGER.info("Save start time: {}ms", System.currentTimeMillis());
                this.repository.save(tollParking);
            });
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
