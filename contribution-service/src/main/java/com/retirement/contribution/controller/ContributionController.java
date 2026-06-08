package com.retirement.contribution.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.retirement.contribution.entity.Contribution;
import com.retirement.contribution.service.ContributionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contributions")
public class ContributionController {

    @Autowired
    private ContributionService service;
    @Operation(summary = "Add monthly contribution")
    @PostMapping
    public Contribution addContribution(@Valid @RequestBody Contribution contribution) {
        return service.addContribution(contribution);
    }
    @Operation(summary = "Get all contributions by user")
    @GetMapping("/{userId}")
    public List<Contribution> getContributions(@PathVariable Long userId) {
        return service.getContributionsByUser(userId);
    }
    @Operation(summary = "Update contribution")
    @PutMapping("/{id}")
    public Contribution updateContribution(
            @PathVariable Long id,
            @Valid @RequestBody Contribution contribution) {

        return service.updateContribution(id, contribution);
    }
    @Operation(summary = "Delete contribution")
    @DeleteMapping("/{id}")
    public String deleteContribution(@PathVariable Long id) {

        return service.deleteContribution(id);
    }
    
}