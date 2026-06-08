package com.retirement.contribution.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.retirement.contribution.dto.NotificationRequest;
import com.retirement.contribution.entity.Contribution;
import com.retirement.contribution.exception.ResourceNotFoundException;
import com.retirement.contribution.repository.ContributionRepository;

@Service
public class ContributionService {

    @Autowired
    private ContributionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Contribution addContribution(Contribution contribution) {

        contribution.setContributionDate(LocalDate.now());

        Contribution savedContribution = repository.save(contribution);

        NotificationRequest request = new NotificationRequest();

        request.setUserId(contribution.getUserId());

        request.setMessage(
                "Contribution of "
                + contribution.getAmount()
                + " received successfully.");

        restTemplate.postForObject(
                "http://localhost:8084/notifications",
                request,
                String.class);

        return savedContribution;
    }

    public List<Contribution> getContributionsByUser(Long userId) {

        List<Contribution> contributions = repository.findByUserId(userId);

        if (contributions.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No contributions found for user " + userId);
        }

        return contributions;
    }
    public Contribution getContributionById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Contribution not found with ID " + id));
    }
    public Contribution updateContribution(
            Long id,
            Contribution updatedContribution) {

        Contribution existingContribution = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Contribution not found with ID " + id));

        existingContribution.setAmount(updatedContribution.getAmount());

        return repository.save(existingContribution);
    }
    public String deleteContribution(Long id) {

        Contribution contribution = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Contribution not found with ID " + id));

        repository.delete(contribution);

        return "Contribution deleted successfully";
    }
}