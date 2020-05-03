package com.grimolizzi.tollParkingRest;

import com.grimolizzi.tollParkingRest.parkings.TollParkingController;
import com.grimolizzi.tollParkingRest.spots.ParkingSpotController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TollParkingRestApplicationTests {

	@Autowired
	ParkingSpotController parkingSpotController;

	@Autowired
	TollParkingController tollParkingController;

	@Test
	void contextLoads() {
		assertThat(parkingSpotController).isNotNull();
		assertThat(tollParkingController).isNotNull();
	}
}
