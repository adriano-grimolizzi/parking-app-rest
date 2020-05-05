package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.errorHandling.CarNotFoundException;
import com.grimolizzi.tollParkingRest.errorHandling.NoAvailableParkingSpotException;
import com.grimolizzi.tollParkingRest.errorHandling.TollParkingNotFoundException;
import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.parkings.TollParkingRepository;
import com.grimolizzi.tollParkingRest.spots.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.grimolizzi.tollParkingRest.spots.model.PossibleCarType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BillingReceipt.class)
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

    @Test(expected = TollParkingNotFoundException.class)
    public void shouldThrowExceptionIfNoTollParkingIsFoundByName() {
        Optional<TollParking> garageOptional = Optional.empty();
        when(this.tollParkingRepository.findByName("Garage1")).thenReturn(garageOptional);
        this.service.retrieveByTollParkingName("Garage1");
    }

    @Test(expected = TollParkingNotFoundException.class)
    public void shouldThrowExceptionIfNoTollParkingIsFoundByCode() {
        Optional<TollParking> garageOptional = Optional.empty();
        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        this.service.retrieveByTollParkingName("G1");
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

    @Test(expected = NoAvailableParkingSpotException.class)
    public void shouldThrowExceptionIfNoAvailableSpotIsFound() {
        Optional<TollParking> garageOptional = Optional.of(
                new TollParking(1L, null, "G1", "Garage1", 2, 2));
        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingIdAndInUseAndPossibleCarType(1L, false, GASOLINE))
                .thenReturn(Optional.empty());

        this.service.retrieveAvailableSpot(new AvailableSpotSearch("G1", GASOLINE));
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

    @Test(expected = CarNotFoundException.class)
    public void shouldThrowExceptionIfNoCarIsFoundWhileHandlingDeparture() {
        Optional<TollParking> garageOptional = Optional.of(
                new TollParking(1L, null, "G1", "Garage1", 2, 2));
        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingIdAndLicensePlate(1L, "BB123CZ"))
                .thenReturn(Optional.empty());
//        PowerMockito.mockStatic(BillingReceipt.class);
//        given(BillingReceipt.getHoursBetween(new Date(1575190800000L), new Date(1575190800000L))).willReturn(2L);
        this.service.handleDeparture(new DepartureRequest("G1", "BB123CZ", new Date(1575190800000L)));
    }

    @Test
    public void shouldHandleDeparture() {
        Optional<TollParking> garageOptional = Optional.of(
                new TollParking(1L, null, "G1", "Garage1", 2, 2));
        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        when(this.parkingSpotRepository.findByTollParkingIdAndLicensePlate(1L, "BB123CZ"))
                .thenReturn(Optional.of(this.getMockedList().get(0)));
        PowerMockito.mockStatic(BillingReceipt.class);
        given(BillingReceipt.getHoursBetween(new Date(1575190800000L), new Date(1575190800000L))).willReturn(2L);
        this.service.handleDeparture(new DepartureRequest("G1", "BB123CZ", new Date(1575190800000L)));

        verify(this.parkingSpotRepository).delete(this.getMockedList().get(0));
        verify(this.parkingSpotRepository).save(this.getMockedList().get(0));
    }

    @Test
    public void shouldSave() {
        TollParking tollParking = new TollParking("G1", "Garage1");

        ParkingSpotCreate toBeCreated = new ParkingSpotCreate("SPOT3", "G1", GASOLINE);
        Optional<TollParking> garageOptional = Optional.of(tollParking);
        when(this.tollParkingRepository.findByCode("G1")).thenReturn(garageOptional);
        this.service.saveParkingSpot(toBeCreated);

        verify(this.parkingSpotRepository).save(new ParkingSpot(tollParking, "SPOT3", GASOLINE));
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
