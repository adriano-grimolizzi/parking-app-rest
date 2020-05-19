package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.requests.ArrivalRequest;
import com.grimolizzi.tollParkingRest.model.PossibleCarType;
import com.grimolizzi.tollParkingRest.parkings.TollParking;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "toll_parking_id", nullable = false)
    private TollParking tollParking;

    private String code;

    @Enumerated(EnumType.STRING)
    private PossibleCarType possibleCarType;

    private String licensePlate; // of occupying car

    private Date timeOfArrival;

    boolean inUse;

    public ParkingSpot(TollParking tollParking, String code, PossibleCarType possibleCarType) {
        this.setTollParking(tollParking);
        this.setCode(code);
        this.setPossibleCarType(possibleCarType);
        // the boolean "inUse" will be automatically set to false
    }

    public void handleRequest(ArrivalRequest request) {
        this.setInUse(true);
        this.setTimeOfArrival(request.getArrivalDate());
        this.setLicensePlate(request.getCarLicensePlate());
    }
}
