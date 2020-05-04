package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.parkings.TollParkingRepository;
import com.grimolizzi.tollParkingRest.spots.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.grimolizzi.tollParkingRest.spots.model.PossibleCarType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotServiceTest {

    @InjectMocks
    private ParkingSpotService service;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private TollParkingRepository tollParkingRepository;

    @Test
    public void shouldRetrieveAllParkingSpots() {

        when(this.parkingSpotRepository.findAll()).thenReturn(this.getMockedList());

        Iterable<ParkingSpot> result = this.service.retrieveAllParkingSpots();

        assertEquals(2, StreamSupport.stream(result.spliterator(), false).count());
    }

    @Test
    public void shouldRetrieveByTollParkingName() {

        Optional<TollParking> garageOptional = Optional.of(new TollParking(1L, null, "G1", "Garage1", 2, 2));

        when(this.tollParkingRepository.findByName("Garage1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingId(1L)).thenReturn(this.getMockedList());
        Iterable<ParkingSpot> result = this.service.retrieveByTollParkingName("Garage1");

        assertEquals(this.getMockedList(), result);
    }

    @Test
    public void shouldRetrieveByTollParkingCode() {

        Optional<TollParking> garageOptional = Optional.of(new TollParking(1L, null, "G1", "Garage1", 2, 2));

        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingId(1L)).thenReturn(this.getMockedList());
        Iterable<ParkingSpot> result = this.service.retrieveByTollParkingCode("G1");

        assertEquals(this.getMockedList(), result);
    }

    private List<ParkingSpot> getMockedList() {

        TollParking tollParking1 = new TollParking("G1", "Garage1");
        TollParking tollParking2 = new TollParking("G2", "Garage2");

        List<ParkingSpot> result = new ArrayList<>();
        result.add(new ParkingSpot(tollParking1, "S1", GASOLINE));
        result.add(new ParkingSpot(tollParking2, "S2", ELECTRIC_20KW));
        return result;
    }
}
