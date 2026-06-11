package com.retirement.plan.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
public class RetirementPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Long userId;

    @Positive(message = "Monthly investment must be greater than 0")
    @NotNull(message = "Monthly investment is required")
    @DecimalMin(value = "500.0",
            message = "Minimum investment is ₹500")
    @DecimalMax(value = "1000000.0",
            message = "Maximum investment allowed is ₹10,00,000")
    private Double monthlyInvestment;

    @Positive(message = "Expected return must be greater than 0")
    @NotNull(message = "Expected return is required")
    @DecimalMin(value = "1.0",
            message = "Expected return must be at least 1%")
    @DecimalMax(value = "30.0",
            message = "Expected return cannot exceed 30%")
    private Double expectedReturn;

    @Positive(message = "Years to retirement must be greater than 0")
    @NotNull(message = "Years to retirement is required")
    @Min(value = 1,
            message = "Years to retirement must be at least 1")
    @Max(value = 50,
            message = "Years to retirement cannot exceed 50")
    private Integer yearsToRetirement;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "INR|USD",
            message = "Currency must be INR or USD")
    private String currency;

    @Column(precision = 19, scale = 2)
    private BigDecimal estimatedCorpus;

    @Transient
    private String estimatedCorpusFormatted;

    public RetirementPlan() {
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getMonthlyInvestment() {
        return monthlyInvestment;
    }

    public void setMonthlyInvestment(Double monthlyInvestment) {
        this.monthlyInvestment = monthlyInvestment;
    }

    public Double getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(Double expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public Integer getYearsToRetirement() {
        return yearsToRetirement;
    }

    public void setYearsToRetirement(Integer yearsToRetirement) {
        this.yearsToRetirement = yearsToRetirement;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getEstimatedCorpus() {
        return estimatedCorpus;
    }

    public void setEstimatedCorpus(BigDecimal estimatedCorpus) {
        this.estimatedCorpus = estimatedCorpus;
    }

    public String getEstimatedCorpusFormatted() {
        return estimatedCorpusFormatted;
    }

    public void setEstimatedCorpusFormatted(
            String estimatedCorpusFormatted) {

        this.estimatedCorpusFormatted =
                estimatedCorpusFormatted;
    }
}