package com.retirement.plan.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.retirement.plan.entity.RetirementPlan;
import com.retirement.plan.repository.RetirementPlanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RetirementPlanServiceTest {

    @Mock
    private RetirementPlanRepository repository;

    @InjectMocks
    private RetirementPlanService service;

    private RetirementPlan plan;

    @BeforeEach
    void setUp() {

        plan = new RetirementPlan();

        plan.setPlanId(1L);
        plan.setUserId(1L);
        plan.setMonthlyInvestment(5000.0);
        plan.setExpectedReturn(10.0);
        plan.setYearsToRetirement(35);
    }

    @Test
    void testCreatePlan() {

        when(repository.save(any(RetirementPlan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RetirementPlan savedPlan = service.createPlan(plan);

        assertNotNull(savedPlan);

        assertTrue(savedPlan.getEstimatedCorpus() > 0);

        verify(repository, times(1))
                .save(any(RetirementPlan.class));
    }

    @Test
    void testGetAllPlans() {

        List<RetirementPlan> plans =
                Arrays.asList(plan);

        when(repository.findAll())
                .thenReturn(plans);

        List<RetirementPlan> result =
                service.getAllPlans();

        assertEquals(1, result.size());

        verify(repository, times(1))
                .findAll();
    }
}