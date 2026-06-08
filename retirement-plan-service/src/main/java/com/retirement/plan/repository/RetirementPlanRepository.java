package com.retirement.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retirement.plan.entity.RetirementPlan;

public interface RetirementPlanRepository extends JpaRepository<RetirementPlan, Long> {

}