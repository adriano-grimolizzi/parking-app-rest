package com.grimolizzi.tollParkingRest;

import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.spots.ParkingSpotRepository;
import com.grimolizzi.tollParkingRest.spots.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.spots.model.PossibleCarType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingSpotRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Test
    public void shouldSaveParkingSpot() {
        TollParking tollParking = new TollParking("parkCode", "parkName");
        this.entityManager.persistAndFlush(tollParking);
        ParkingSpot parkingSpot = new ParkingSpot(tollParking, "code", PossibleCarType.GASOLINE);
        parkingSpot.setLicensePlate("asdf");
        this.entityManager.persistAndFlush(parkingSpot);

        assertThat(tollParking.getId()).isNotNull();
        assertThat(parkingSpot.getId()).isNotNull();
        assertThat(this.parkingSpotRepository.findByTollParkingId(tollParking.getId())).isNotNull();
        assertThat(this.parkingSpotRepository.findByTollParkingIdAndInUseAndPossibleCarType(
                tollParking.getId(), false, PossibleCarType.GASOLINE)).isNotNull();
        assertThat(this.parkingSpotRepository.findByTollParkingIdAndLicensePlate(
                tollParking.getId(), "asdf")).isNotNull();
    }

    @Test
    public void shouldDeleteParkingSpot() {
        TollParking tollParking = new TollParking("parkCode", "parkName");
        this.entityManager.persistAndFlush(tollParking);
        ParkingSpot parkingSpot = new ParkingSpot(tollParking, "code", PossibleCarType.GASOLINE);
        parkingSpot.setLicensePlate("asdf");
        this.entityManager.persistAndFlush(parkingSpot);

        this.parkingSpotRepository.deleteAll();
        assertThat(this.parkingSpotRepository.findAll().isEmpty());
    }
}
