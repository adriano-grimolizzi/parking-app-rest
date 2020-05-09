package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.errorHandling.CarNotFoundException;
import com.grimolizzi.tollParkingRest.errorHandling.NoAvailableParkingSpotException;
import com.grimolizzi.tollParkingRest.errorHandling.TollParkingNotFoundException;
import com.grimolizzi.tollParkingRest.model.*;
import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.parkings.TollParkingRepository;
import com.grimolizzi.tollParkingRest.requests.ArrivalRequest;
import com.grimolizzi.tollParkingRest.requests.DepartureRequest;
import com.grimolizzi.tollParkingRest.requests.ParkingSpotCreate;
import com.grimolizzi.tollParkingRest.spots.model.AvailableSpotSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    private final TollParkingRepository tollParkingRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingSpotService(
            TollParkingRepository tollParkingRepository,
            ParkingSpotRepository parkingSpotRepository) {
        this.tollParkingRepository = tollParkingRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public Iterable<ParkingSpot> retrieveAllParkingSpots() {
        return this.parkingSpotRepository.findAll();
    }

    public Iterable<ParkingSpot> retrieveByTollParkingName(String tollParkingName) {
        TollParking tollParking = this.tollParkingRepository.findByName(tollParkingName)
                .orElseThrow(() -> new TollParkingNotFoundException("name", tollParkingName));
        return this.parkingSpotRepository.findByTollParkingId(tollParking.getId());
    }

    public Iterable<ParkingSpot> retrieveByTollParkingCode(String tollParkingCode) {
        TollParking tollParking = this.retrieveTollParkingOrThrowException(tollParkingCode);
        return this.parkingSpotRepository.findByTollParkingId(tollParking.getId());
    }

    public Iterable<ParkingSpot> retrieveAvailableSpot(AvailableSpotSearch search) {
        TollParking tollParking = this.retrieveTollParkingOrThrowException(search.getTollParkingCode());
        return this.parkingSpotRepository.findByTollParkingIdAndInUseAndPossibleCarType(
                tollParking.getId(), false, search.getPossibleCarType())
                .orElseThrow(() -> new NoAvailableParkingSpotException(
                        search.getTollParkingCode(), search.getPossibleCarType()));
    }

    public void saveParkingSpot(ParkingSpotCreate parkingSpotCreate) {
        TollParking tollParking = this.retrieveTollParkingOrThrowException(parkingSpotCreate.getTollParkingCode());
        this.parkingSpotRepository.save(new ParkingSpot(
                tollParking, parkingSpotCreate.getCode(), parkingSpotCreate.getPossibleCarType()));
    }

    public void handleArrival(ArrivalRequest arrivalRequest) {

        Iterable<ParkingSpot> availableSpots = this.retrieveAvailableSpot(
                new AvailableSpotSearch(arrivalRequest.getTollParkingCode(), arrivalRequest.getPossibleCarType()));

        // Take the first available spot
        ParkingSpot parkingSpot = availableSpots.iterator().next();
        this.parkingSpotRepository.delete(parkingSpot);
        parkingSpot.handleRequest(arrivalRequest);
        this.parkingSpotRepository.save(parkingSpot);
    }

    public BillingReceipt handleDeparture(DepartureRequest departureRequest) {
        String tollParkingCode = departureRequest.getTollParkingCode();
        String licensePlate = departureRequest.getCarLicensePlate();

        TollParking tollParking = this.retrieveTollParkingOrThrowException(tollParkingCode);
        ParkingSpot parkingSpot = this.parkingSpotRepository
                .findByTollParkingIdAndLicensePlate(tollParking.getId(), licensePlate)
                .orElseThrow(() -> new CarNotFoundException(tollParkingCode, licensePlate));

        this.parkingSpotRepository.delete(parkingSpot);
        parkingSpot.setInUse(false);
        parkingSpot.setLicensePlate(null);
        this.parkingSpotRepository.save(parkingSpot);

        return new BillingReceipt(
                parkingSpot.getTimeOfArrival(),
                departureRequest.getDepartureDate(),
                tollParking.getHourlyRate(),
                tollParking.getFixedAmount()
        );
    }

    private TollParking retrieveTollParkingOrThrowException(String code) {
        return this.tollParkingRepository.findByCode(code)
                .orElseThrow(() -> new TollParkingNotFoundException("code", code));
    }
}
