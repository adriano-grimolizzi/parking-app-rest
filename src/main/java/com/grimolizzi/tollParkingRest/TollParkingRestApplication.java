package com.grimolizzi.tollParkingRest;

import com.grimolizzi.tollParkingRest.spots.ParkingSpot;
import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.spots.ParkingSpotRepository;
import com.grimolizzi.tollParkingRest.parkings.TollParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.grimolizzi.tollParkingRest.model.PossibleCarType.*;

@SpringBootApplication
public class TollParkingRestApplication {

	@Autowired
	private TollParkingRepository tollParkingRepository;

	@Autowired
	private ParkingSpotRepository parkingSpotRepository;

	public static void main(String[] args) {
		SpringApplication.run(TollParkingRestApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> initMockData();
	}

	public void initMockData() {
//		TollParking mockParking1 = new TollParking("mockParkCode1", "mockParkName1", 10, 1);
//		TollParking mockParking2 = new TollParking("mockParkCode2", "mockParkName2", 20, 2);
//		this.tollParkingRepository.save(mockParking1);
//		this.tollParkingRepository.save(mockParking2);

//		this.parkingSpotRepository.save(
//				new ParkingSpot(mockParking1, "mockSpotCode1", GASOLINE));
//		this.parkingSpotRepository.save(
//				new ParkingSpot(mockParking2, "mockSpotCode2", ELECTRIC_20KW));
//		this.parkingSpotRepository.save(
//				new ParkingSpot(mockParking1, "mockSpotCode3", ELECTRIC_50KW));
	}
}
