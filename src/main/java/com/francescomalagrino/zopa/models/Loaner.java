package com.francescomalagrino.zopa.models;

//Loaner is the leander

	public class Loaner  {
		private String name;

	    private double rate;

	    private int amount;

	    public Loaner(String name, double rate, int amount) {
	        this.name = name;
	        this.rate = rate;
	        this.amount = amount;
	    }

	    public String getName() {
	        return name;
	    }

	    public double getRate() {
	        return rate;
	    }

	    public int getAmount() {
	        return amount;
	    }
	    
}