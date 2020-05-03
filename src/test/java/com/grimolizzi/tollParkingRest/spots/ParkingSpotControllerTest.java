package com.grimolizzi.tollParkingRest.spots;

import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.spots.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.spots.model.PossibleCarType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotControllerTest {

    private static final String URL_TEMPLATE = "/parkingSpots";

    @InjectMocks
    private ParkingSpotController controller;

    @Mock
    private ParkingSpotService service;

    private MockMvc mvc;

    @Test
    public void shouldGetAll() throws Exception {

        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(service.retrieveAllParkingSpots()).thenReturn(getMockedList());

        this.mvc.perform(get(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", equalTo("1A")));
    }

    private List<ParkingSpot> getMockedList() {

        TollParking tollParking1 = new TollParking();
        tollParking1.setCode("5S");
        tollParking1.setName("5th Street");
        tollParking1.setFixedAmount(10);
        tollParking1.setHourlyRate(2);

        ParkingSpot spot1 = new ParkingSpot();
        spot1.setCode("1A");
        spot1.setPossibleCarType(PossibleCarType.ELECTRIC_20KW);
        spot1.setTollParking(tollParking1);
        spot1.setLicensePlate("BB345AE");
        spot1.setInUse(true);
        spot1.setTimeOfArrival(new Date());

        List<ParkingSpot> result = new ArrayList<>();
        result.add(spot1);
        return result;
    }
}
