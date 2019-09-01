package com.francescomalagrino.zopa.models;


import com.francescomalagrino.zopa.exceptions.AvailableAmountException;


public class Quote {

    private double monthlyRate;

    private double monthlyRepayment;

    private double totalRepayment;

    public Quote(double monthlyRate, double monthlyRepayment, double totalRepayment) throws AvailableAmountException{
        this.monthlyRate = monthlyRate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public double getTotalRepayment() {
        return totalRepayment;
    }
}

