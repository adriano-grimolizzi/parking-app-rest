package com.grimolizzi.tollParkingRest.parkings;

import com.grimolizzi.tollParkingRest.parkings.TollParking;
import org.springframework.data.repository.CrudRepository;

public interface TollParkingRepository extends CrudRepository<TollParking, Long> {

    TollParking findByName(String name);

    TollParking findByCode(String code);
}
