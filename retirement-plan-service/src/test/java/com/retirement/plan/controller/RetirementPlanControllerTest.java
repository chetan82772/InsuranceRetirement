package com.retirement.plan.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retirement.plan.entity.RetirementPlan;
import com.retirement.plan.service.RetirementPlanService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RetirementPlanController.class)
public class RetirementPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetirementPlanService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatePlan() throws Exception {

        RetirementPlan plan = new RetirementPlan();

        plan.setPlanId(1L);
        plan.setUserId(1L);
        plan.setMonthlyInvestment(5000.0);
        plan.setExpectedReturn(10.0);
        plan.setYearsToRetirement(35);
        plan.setEstimatedCorpus(1000000.0);

        when(service.createPlan(any(RetirementPlan.class)))
                .thenReturn(plan);

        mockMvc.perform(post("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId")
                        .value(1))
                .andExpect(jsonPath("$.estimatedCorpus")
                        .value(1000000.0));
    }

    @Test
    void testGetPlans() throws Exception {

        RetirementPlan plan = new RetirementPlan();

        plan.setPlanId(1L);
        plan.setUserId(1L);

        when(service.getAllPlans())
                .thenReturn(Arrays.asList(plan));

        mockMvc.perform(get("/plans"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId")
                        .value(1));
    }
}