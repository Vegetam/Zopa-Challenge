package com.francescomalagrino.zopa.services;

import com.francescomalagrino.zopa.exceptions.AvailableAmountException;
import com.francescomalagrino.zopa.models.Loaner;
import com.francescomalagrino.zopa.models.Quote;
import com.francescomalagrino.zopa.CVS.Parser.CSVParser;
import com.francescomalagrino.zopa.permanent.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class LoanService {
	private static final int LOAN_LENGTH = 36;

    private List<Loaner> loaners;

    private int availableAmount;

    public LoanService(String csv) throws IOException {
        this.loaners = CSVParser.parseCSV(csv);
        Collections.sort(loaners, Comparator.comparingDouble(Loaner::getRate));
        for (Loaner loaner : loaners) {
            availableAmount += loaner.getAmount();
        }
    }

    public Quote getRate(int desiredAmount) throws AvailableAmountException {
        checkAvailableAmountExceeded(desiredAmount);

        double totalInterest = 0.0;

        int remainingAmount = desiredAmount;
        for (Loaner loaner : loaners) {
        	//getting the min borrow
            int borrowed = Math.min(remainingAmount, loaner.getAmount());
           totalInterest += borrowed * loaner.getRate();
            remainingAmount -= borrowed;
            if (remainingAmount == 0)
                break;
        }

        double annualRate = totalInterest / desiredAmount;
        double interestRatePerMonth = Math.pow((1 + annualRate), (1. / 12.)) - 1;

        double monthlyPayment = (interestRatePerMonth * desiredAmount) /
                (1 - (Math.pow(1 + interestRatePerMonth, -LOAN_LENGTH)));



        return new Quote(annualRate, monthlyPayment, monthlyPayment * LOAN_LENGTH);
    }

    private void checkAvailableAmountExceeded(int desiredAmount) throws AvailableAmountException {
        if (availableAmount < desiredAmount) {
        	System.out.println(Permanent.AVAILABLE_LOAN);
        }
    }

    public static int getLoanLength(){
        return LOAN_LENGTH;
    }

    public List<Loaner> getLenders() {
        return loaners;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }
   
    
}
