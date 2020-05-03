package com.grimolizzi.tollParkingRest;

import com.grimolizzi.tollParkingRest.model.ArrivalRequest;
import com.grimolizzi.tollParkingRest.model.PossibleCarType;

import java.util.Date;

public class MainTest {

    public static void main(String...args) {
        ArrivalRequest r = new ArrivalRequest("5S", "BB354LL", PossibleCarType.GASOLINE, new Date());
    }
}
