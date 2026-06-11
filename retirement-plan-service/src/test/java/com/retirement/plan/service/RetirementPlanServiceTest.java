package com.retirement.plan.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
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
        plan.setCurrency("INR");
    }

    @Test
    void testCreatePlan() {

        when(repository.save(any(RetirementPlan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RetirementPlan result =
                service.createPlan(plan);

        assertNotNull(result);

        assertEquals(
                1L,
                result.getUserId());

        assertEquals(
                "INR",
                result.getCurrency());

        assertNotNull(
                result.getEstimatedCorpus());

        assertTrue(
                result.getEstimatedCorpus()
                        .compareTo(BigDecimal.ZERO) > 0);

        assertNotNull(
                result.getEstimatedCorpusFormatted());

        verify(repository, times(1))
                .save(any(RetirementPlan.class));
    }

    @Test
    void testGetAllPlans() {

        plan.setEstimatedCorpus(
                BigDecimal.valueOf(19022238.25));

        when(repository.findAll())
                .thenReturn(Arrays.asList(plan));

        List<RetirementPlan> result =
                service.getAllPlans();

        assertEquals(
                1,
                result.size());

        assertEquals(
                "₹1.90 Crore",
                result.get(0)
                        .getEstimatedCorpusFormatted());

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void testCreatePlanInvalidCurrency() {

        plan.setCurrency("EUR");

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.createPlan(plan));

        assertEquals(
                "Currency must be INR or USD",
                exception.getMessage());
    }

    @Test
    void testCreatePlanInvalidInvestment() {

        plan.setMonthlyInvestment(0.0);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.createPlan(plan));

        assertEquals(
                "Monthly investment must be greater than 0",
                exception.getMessage());
    }

    @Test
    void testCreatePlanInvalidReturn() {

        plan.setExpectedReturn(40.0);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.createPlan(plan));

        assertEquals(
                "Expected return must be between 1 and 30",
                exception.getMessage());
    }

    @Test
    void testCreatePlanInvalidYears() {

        plan.setYearsToRetirement(60);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.createPlan(plan));

        assertEquals(
                "Years to retirement must be between 1 and 50",
                exception.getMessage());
    }
}