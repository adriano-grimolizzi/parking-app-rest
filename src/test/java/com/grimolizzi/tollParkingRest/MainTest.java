package com.grimolizzi.tollParkingRest;

import com.grimolizzi.tollParkingRest.spots.model.ArrivalRequest;
import com.grimolizzi.tollParkingRest.spots.model.PossibleCarType;

import java.util.Date;

public class MainTest {

    public static void main(String...args) {
        ArrivalRequest r = new ArrivalRequest("5S", "BB354LL", PossibleCarType.GASOLINE, new Date());
    }
}
