package com.retirement.contribution.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retirement.contribution.entity.Contribution;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    List<Contribution> findByUserId(Long userId);
}