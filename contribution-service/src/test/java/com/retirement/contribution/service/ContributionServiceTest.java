package com.retirement.contribution.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.retirement.contribution.entity.Contribution;
import com.retirement.contribution.exception.ResourceNotFoundException;
import com.retirement.contribution.repository.ContributionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class ContributionServiceTest {

    @Mock
    private ContributionRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ContributionService service;

    private Contribution contribution;

    @BeforeEach
    void setUp() {

        contribution = new Contribution();

        contribution.setContributionId(1L);
        contribution.setUserId(1L);
        contribution.setAmount(5000);
        contribution.setContributionDate(LocalDate.now());
    }

    @Test
    void testAddContribution() {

        when(repository.save(any(Contribution.class)))
                .thenReturn(contribution);

        when(restTemplate.postForObject(
                anyString(),
                any(),
                eq(String.class)))
                .thenReturn("Notification Sent");

        Contribution result =
                service.addContribution(contribution);

        assertNotNull(result);

        verify(repository, times(1))
                .save(any(Contribution.class));
    }

    @Test
    void testGetContributionsByUser() {

        when(repository.findByUserId(1L))
                .thenReturn(Arrays.asList(contribution));

        List<Contribution> result =
                service.getContributionsByUser(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetContributionsByUser_NotFound() {

        when(repository.findByUserId(99L))
                .thenReturn(Collections.emptyList());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getContributionsByUser(99L));
    }

    @Test
    void testGetContributionById() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(contribution));

        Contribution result =
                service.getContributionById(1L);

        assertNotNull(result);
    }

    @Test
    void testUpdateContribution() {

        Contribution updated =
                new Contribution();

        updated.setAmount(7000);

        when(repository.findById(1L))
                .thenReturn(Optional.of(contribution));

        when(repository.save(any(Contribution.class)))
                .thenReturn(contribution);

        Contribution result =
                service.updateContribution(1L, updated);

        assertNotNull(result);
    }

    @Test
    void testDeleteContribution() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(contribution));

        String result =
                service.deleteContribution(1L);

        assertEquals(
                "Contribution deleted successfully",
                result);
    }
}