package com.grimolizzi.tollParkingRest.spots;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grimolizzi.tollParkingRest.parkings.TollParking;
import com.grimolizzi.tollParkingRest.spots.model.AvailableSpotSearch;
import com.grimolizzi.tollParkingRest.spots.model.ParkingSpot;
import com.grimolizzi.tollParkingRest.spots.model.ParkingSpotCreate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static com.grimolizzi.tollParkingRest.spots.model.PossibleCarType.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotControllerTest {

    private static final String URL_TEMPLATE = "/parkingSpots";

    @InjectMocks
    private ParkingSpotController controller;

    @Mock
    private ParkingSpotService service;

    private MockMvc mvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldGetAll() throws Exception {

        when(service.retrieveAllParkingSpots()).thenReturn(getMockedList());

        this.mvc.perform(get(URL_TEMPLATE)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].code", equalTo("S1")))
                .andExpect(jsonPath("$[1].code", equalTo("S2")));
    }

    @Test
    public void shouldFindByTollParkingName() throws Exception {

        when(this.service.retrieveByTollParkingName("Garage1")).thenReturn(
                Collections.singletonList(getMockedList().get(0)));

        this.mvc.perform(get(URL_TEMPLATE + "/tollParkingName/Garage1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", equalTo("S1")));
    }

    @Test
    public void shouldFindByTollParkingCode() throws Exception {

        when(this.service.retrieveByTollParkingCode("G2")).thenReturn(
                Collections.singletonList(getMockedList().get(1)));

        this.mvc.perform(get(URL_TEMPLATE + "/tollParkingCode/G2")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", equalTo("S2")));
    }

    @Test
    public void shouldRetrieveAvailableSpot() throws Exception {

        AvailableSpotSearch search = new AvailableSpotSearch("G2", ELECTRIC_50KW);

        this.mvc.perform(get(URL_TEMPLATE + "/available/tollParkingCode/G2/possibleCarType/ELECTRIC_50KW")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.service).retrieveAvailableSpot(search);
    }

    @Test
    public void shouldSave() throws Exception {

        ParkingSpotCreate toBeCreated = new ParkingSpotCreate("SPOT3", "G3", GASOLINE);

        String jsonBody = new ObjectMapper().writeValueAsString(toBeCreated);

        this.mvc.perform(post(URL_TEMPLATE + "/create")
                .content(jsonBody)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.service).saveParkingSpot(toBeCreated);
    }

    private List<ParkingSpot> getMockedList() {

        TollParking tollParking1 = new TollParking("G1", "Garage1");
        TollParking tollParking2 = new TollParking("G2", "Garage2");

        ParkingSpot spot1 = new ParkingSpot(tollParking1, "S1", GASOLINE);
        ParkingSpot spot2 = new ParkingSpot(tollParking2, "S2", ELECTRIC_20KW);
        ParkingSpot spot3 = new ParkingSpot(tollParking2, "S3", ELECTRIC_50KW);
        spot3.setInUse(true);

        List<ParkingSpot> result = new ArrayList<>();
        result.add(spot1);
        result.add(spot2);
        return result;
    }
}
