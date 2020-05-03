package com.grimolizzi.tollParkingRest.parkings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@WebMvcTest(TollParkingController.class)
public class TollParkingControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private TollParkingRepository repository;
//
//    private static final String URL_TEMPLATE = "/tollParkings";
//
//    @Test
//    public void shouldGetAll() throws Exception {
//
//        given(this.repository.findAll()).willReturn(getMockedList());
//
//        this.mvc.perform(get(URL_TEMPLATE)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", equalTo("name")));
//    }
//
//
//    private List<TollParking> getMockedList() {
//        List<TollParking> list = new ArrayList<>();
//        list.add(new TollParking("code", "name"));
//        return list;
//    }
}
