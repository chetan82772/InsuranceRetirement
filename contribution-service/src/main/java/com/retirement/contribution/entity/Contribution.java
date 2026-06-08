package com.retirement.contribution.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contributionId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Min(value = 1, message = "Amount must be greater than 0")
    private double amount;

    private LocalDate contributionDate;

    public Contribution() {
    }

    public Long getContributionId() {
        return contributionId;
    }

    public void setContributionId(Long contributionId) {
        this.contributionId = contributionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getContributionDate() {
        return contributionDate;
    }

    public void setContributionDate(LocalDate contributionDate) {
        this.contributionDate = contributionDate;
    }
}