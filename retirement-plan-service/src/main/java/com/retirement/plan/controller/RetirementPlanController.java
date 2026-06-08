package com.retirement.plan.controller;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.retirement.plan.entity.RetirementPlan;
import com.retirement.plan.service.RetirementPlanService;

@RestController
@RequestMapping("/plans")
public class RetirementPlanController {

    @Autowired
    private RetirementPlanService service;

    @PostMapping
    public RetirementPlan createPlan(@Valid @RequestBody RetirementPlan plan) {
        return service.createPlan(plan);
    }

    @GetMapping
    public List<RetirementPlan> getPlans() {
        return service.getAllPlans();
    }
}