package com.grimolizzi.tollParkingRest;

import com.grimolizzi.tollParkingRest.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.model.PossibleCarType;
import com.grimolizzi.tollParkingRest.model.TollParking;
import com.grimolizzi.tollParkingRest.repository.ParkingSpotRepository;
import com.grimolizzi.tollParkingRest.repository.TollParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

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
		return args -> {
			TollParking tollParking1 = new TollParking();
			tollParking1.setCode("5S");
			tollParking1.setName("5th Street");
			this.tollParkingRepository.save(tollParking1);

			TollParking tollParking2 = new TollParking();
			tollParking2.setCode("1A");
			tollParking2.setName("10th Avenue");
			this.tollParkingRepository.save(tollParking2);

			ParkingSpot spot1 = new ParkingSpot();
			spot1.setCode("1A");
			spot1.setPossibleCarType(PossibleCarType.ELECTRIC_20KW);
			spot1.setTollParking(tollParking1);
			spot1.setLicensePlate("BB345AE");
			spot1.setInUse(true);
			spot1.setTimeOfArrival(new Date());
			this.parkingSpotRepository.save(spot1);

			ParkingSpot spot2 = new ParkingSpot();
			spot2.setCode("2A");
			spot2.setPossibleCarType(PossibleCarType.GASOLINE);
			spot2.setTollParking(tollParking1);
			spot2.setLicensePlate("FD45390");
			spot2.setInUse(false);
			spot2.setTimeOfArrival(new Date());
			this.parkingSpotRepository.save(spot2);

			ParkingSpot spot3 = new ParkingSpot();
			spot3.setCode("2B");
			spot3.setPossibleCarType(PossibleCarType.ELECTRIC_50KW);
			spot3.setTollParking(tollParking2);
			spot3.setLicensePlate("ER345RR");
			spot3.setInUse(false);
			spot3.setTimeOfArrival(new Date());
			this.parkingSpotRepository.save(spot3);
		};
	}
}
