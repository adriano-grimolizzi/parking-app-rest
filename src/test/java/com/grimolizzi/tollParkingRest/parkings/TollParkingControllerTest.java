package com.grimolizzi.tollParkingRest.parkings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TollParkingControllerTest {

    private static final String URL_TEMPLATE = "/tollParkings";

    @InjectMocks
    private TollParkingController controller;

    @Mock
    private TollParkingRepository repository;

    private MockMvc mvc;

    @Before
    public void init() {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldGetAll() throws Exception {

        given(this.repository.findAll()).willReturn(getMockedList());

        this.mvc.perform(get(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", equalTo("name1")))
                .andExpect(jsonPath("$[0].code", equalTo("code1")));
    }

    @Test
    public void shouldSave() throws Exception {

        TollParking toSave = new TollParking("code1", "name1");

        this.mvc.perform(post(URL_TEMPLATE + "/code/code1/name/name1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.repository).save(toSave);
    }

    private List<TollParking> getMockedList() {
        List<TollParking> list = new ArrayList<>();
        list.add(new TollParking("code1", "name1"));
        list.add(new TollParking("code2", "name2"));
        return list;
    }
}
