package com.grimolizzi.tollParkingRest;

import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.parkings.TollParkingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TollParkingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TollParkingRepository tollParkingRepository;

    @Test
    public void shouldSaveParkingToll() {
        TollParking tollParking = new TollParking("parkCode", "parkName");
        this.entityManager.persistAndFlush(tollParking);
        assertThat(tollParking.getId()).isNotNull();
    }

    @Test
    public void shouldDeleteParkingToll() {
        this.entityManager.persistAndFlush(new TollParking("code1", "name1"));
        this.entityManager.persistAndFlush(new TollParking("code2", "name2"));
        this.tollParkingRepository.deleteAll();
        assertThat(this.tollParkingRepository.findAll().isEmpty());
    }

    @Test
    public void shouldFindByName() {
        this.tollParkingRepository.save(new TollParking("code1", "name1"));
        assertThat(this.tollParkingRepository.findByName("name1")).isNotNull();
    }

    @Test
    public void shouldFindByCode() {
        this.tollParkingRepository.save(new TollParking("code1", "name1"));
        assertThat(this.tollParkingRepository.findByCode("code1")).isNotNull();
    }
}
