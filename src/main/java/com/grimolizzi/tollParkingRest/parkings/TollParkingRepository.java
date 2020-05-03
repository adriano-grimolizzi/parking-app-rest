package com.grimolizzi.tollParkingRest.parkings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TollParkingRepository extends JpaRepository<TollParking, Long> {

    Optional<TollParking> findByName(String name);

    Optional<TollParking> findByCode(String code);
}
