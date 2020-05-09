package com.grimolizzi.tollParkingRest.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTollParkingRequest {
    private String code;
    private String name;
}
