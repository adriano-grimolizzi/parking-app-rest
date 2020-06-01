package com.grimolizzi.tollParkingRest.parkings;

import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface TollParkingRepository extends Repository<TollParking, Long> {

    @Async
    CompletableFuture<List<TollParking>> findAll();

    @Async
    <S extends TollParking> Future<S> save(TollParking tollParking);

    Optional<TollParking> findByName(String name);

    Optional<TollParking> findByCode(String code);
}
