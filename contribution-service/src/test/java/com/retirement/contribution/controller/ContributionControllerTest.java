package com.retirement.contribution.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retirement.contribution.entity.Contribution;
import com.retirement.contribution.service.ContributionService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ContributionController.class)
public class ContributionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContributionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddContribution() throws Exception {

        Contribution contribution =
                new Contribution();

        contribution.setContributionId(1L);
        contribution.setUserId(1L);
        contribution.setAmount(5000);
        contribution.setContributionDate(LocalDate.now());

        when(service.addContribution(any(Contribution.class)))
                .thenReturn(contribution);

        mockMvc.perform(post("/contributions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contribution)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId")
                        .value(1));
    }

    @Test
    void testGetContributions() throws Exception {

        Contribution contribution =
                new Contribution();

        contribution.setContributionId(1L);
        contribution.setUserId(1L);

        when(service.getContributionsByUser(1L))
                .thenReturn(Arrays.asList(contribution));

        mockMvc.perform(get("/contributions/1"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId")
                        .value(1));
    }

    @Test
    void testDeleteContribution() throws Exception {

        when(service.deleteContribution(1L))
                .thenReturn("Contribution deleted successfully");

        mockMvc.perform(delete("/contributions/1"))

                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Contribution deleted successfully"));
    }
}