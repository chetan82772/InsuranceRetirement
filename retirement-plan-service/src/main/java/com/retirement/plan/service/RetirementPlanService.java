package com.retirement.plan.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retirement.plan.entity.RetirementPlan;
import com.retirement.plan.repository.RetirementPlanRepository;

@Service
public class RetirementPlanService {

    @Autowired
    private RetirementPlanRepository repository;

    public RetirementPlan createPlan(RetirementPlan plan) {

        // Business Validations

        if (plan.getMonthlyInvestment() <= 0) {
            throw new IllegalArgumentException(
                    "Monthly investment must be greater than 0");
        }

        if (plan.getExpectedReturn() <= 0
                || plan.getExpectedReturn() > 30) {

            throw new IllegalArgumentException(
                    "Expected return must be between 1 and 30");
        }

        if (plan.getYearsToRetirement() <= 0
                || plan.getYearsToRetirement() > 50) {

            throw new IllegalArgumentException(
                    "Years to retirement must be between 1 and 50");
        }

        // Currency Validation

        if (!"INR".equals(plan.getCurrency())
                && !"USD".equals(plan.getCurrency())) {

            throw new IllegalArgumentException(
                    "Currency must be INR or USD");
        }

        // Retirement Corpus Calculation

        double monthlyRate =
                plan.getExpectedReturn() / 100 / 12;

        int months =
                plan.getYearsToRetirement() * 12;

        double corpus =
                plan.getMonthlyInvestment()
                * ((Math.pow(1 + monthlyRate, months) - 1)
                / monthlyRate)
                * (1 + monthlyRate);

        // Outbound Validation

        if (Double.isNaN(corpus)
                || Double.isInfinite(corpus)) {

            throw new IllegalStateException(
                    "Invalid corpus calculated");
        }

        if (corpus <= 0) {

            throw new IllegalStateException(
                    "Calculated corpus must be greater than zero");
        }

        // Convert to BigDecimal

        BigDecimal estimatedCorpus =
                BigDecimal.valueOf(corpus)
                .setScale(2, RoundingMode.HALF_UP);

        plan.setEstimatedCorpus(estimatedCorpus);

        RetirementPlan savedPlan =
                repository.save(plan);

        savedPlan.setEstimatedCorpusFormatted(
                formatCurrency(
                        estimatedCorpus,
                        savedPlan.getCurrency()));

        return savedPlan;
    }

    public List<RetirementPlan> getAllPlans() {

        List<RetirementPlan> plans =
                repository.findAll();

        plans.forEach(plan -> {

            if (plan.getEstimatedCorpus() != null) {

                plan.setEstimatedCorpusFormatted(
                        formatCurrency(
                                plan.getEstimatedCorpus(),
                                plan.getCurrency()));
            }
        });

        return plans;
    }

    private String formatCurrency(
            BigDecimal amount,
            String currency) {

        double value = amount.doubleValue();

        if ("INR".equals(currency)) {

            return formatIndianCurrency(value);

        } else {

            return formatUSCurrency(value);
        }
    }

    private String formatIndianCurrency(
            double amount) {

        if (amount >= 10000000) {

            return String.format(
                    "₹%.2f Crore",
                    amount / 10000000);
        }

        if (amount >= 100000) {

            return String.format(
                    "₹%.2f Lakh",
                    amount / 100000);
        }

        return String.format(
                "₹%,.2f",
                amount);
    }

    private String formatUSCurrency(
            double amount) {

        if (amount >= 1000000000) {

            return String.format(
                    "$%.2f Billion",
                    amount / 1000000000);
        }

        if (amount >= 1000000) {

            return String.format(
                    "$%.2f Million",
                    amount / 1000000);
        }

        return String.format(
                "$%,.2f",
                amount);
    }
}