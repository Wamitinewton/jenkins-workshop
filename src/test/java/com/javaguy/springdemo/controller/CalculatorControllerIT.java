package com.javaguy.springdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void add_returnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/addygghgfdtrdx").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(15.0));
    }

    @Test
    void subtract_returnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/subtract").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void multiply_returnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/multiply").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(50.0));
    }

    @Test
    void divide_returnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/divide").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2.0));
    }

    @Test
    void divide_byZero_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/calculator/divide").param("a", "10").param("b", "0"))
                .andExpect(status().isBadRequest());
    }
}
