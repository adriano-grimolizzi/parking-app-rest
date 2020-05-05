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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.grimolizzi.tollParkingRest.spots.model.PossibleCarType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
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

        assertEquals(this.getMockedList(), this.service.retrieveByTollParkingName("Garage1"));
    }

    @Test
    public void shouldRetrieveByTollParkingCode() {

        Optional<TollParking> garageOptional = Optional.of(new TollParking(1L, null, "G1", "Garage1", 2, 2));

        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingId(1L)).thenReturn(this.getMockedList());

        assertEquals(this.getMockedList(), this.service.retrieveByTollParkingCode("G1"));
    }

    @Test
    public void shouldRetrieveAvailableSpot() {

        Optional<TollParking> garageOptional = Optional.of(
                new TollParking(1L, null, "G1", "Garage1", 2, 2));

        Optional<Iterable<ParkingSpot>> spotsOptional = Optional.of(this.getMockedList());

        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingIdAndInUseAndPossibleCarType(1L, false, GASOLINE))
                .thenReturn(spotsOptional);

        assertEquals(this.getMockedList(),
                this.service.retrieveAvailableSpot(new AvailableSpotSearch("G1", GASOLINE)));
    }

    @Test
    public void shouldHandleArrival() {
        Optional<TollParking> garageOptional = Optional.of(
                new TollParking(1L, null, "G1", "Garage1", 2, 2));

        Optional<Iterable<ParkingSpot>> spotsOptional = Optional.of(this.getMockedList());

        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingIdAndInUseAndPossibleCarType(1L, false, GASOLINE))
                .thenReturn(spotsOptional);

        this.service.handleArrival(
                new ArrivalRequest("G1", "AS123QW", GASOLINE, new Date(1575190800000L)));

        ParkingSpot toBeVerified = this.getMockedList().get(0);
        // Handling request
        toBeVerified.setLicensePlate("AS123QW");
        toBeVerified.setInUse(true);
        toBeVerified.setTimeOfArrival(new Date(1575190800000L));

        verify(this.parkingSpotRepository).delete(toBeVerified);
        // I don't know why, but in the test this delete method is called with the parking spot
        // AFTER the request is handled. In the actual code it's called BEFORE.
        verify(this.parkingSpotRepository).save(toBeVerified);
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
