package com.grimolizzi.tollParkingRest.repository;

import com.grimolizzi.tollParkingRest.model.TollParking;
import org.springframework.data.repository.CrudRepository;

public interface TollParkingRepository extends CrudRepository<TollParking, Long> {

    TollParking findByName(String name);

    TollParking findByCode(String code);
}
