package com.jpmc.service.strategy.impl;

import org.apache.log4j.Logger;

import com.jpmc.dto.Input;
import com.jpmc.dto.Output;
import com.jpmc.service.strategy.abs.Strategy;

/**
 * @author Aniket Kulkarni
 * This class is Strategy to calculate Index for P/E ratio
 */
public class PeRatioCalculatorStrategy implements Strategy {

	private final static Logger logger = Logger.getLogger(PeRatioCalculatorStrategy.class);
	private Input input; 
	
	/* (non-Javadoc)
	 * @see com.jpmc.service.strategy.abs.Strategy#setInput(com.jpmc.dto.Input)
	 */
	public void setInput(Input input) {
		this.input = input;
	}

	/** 
	 * Calculates Index for the given stock and price.
	 * Formula to calculate is (Price)/(Stock Fixed Dividend). 
	 * Price is entered by user. Fixed dividend is pre-populated with stock.
	 * Returns the result String to be displayed on console.
	 * Validations done on user input for Stock name and price.
	 * @return Output 
	 * @throws NullPointerException, IllegalArgumentException
	 */
	public Output performOperation() throws Exception {

		ValidateInput();
		return new Output(getPeRationResult());
	}

	private String getPeRationResult() {
		logger.debug("Calculating P/E ratio...");
		if(input.getStock().getLastDivident() == null || input.getStock().getLastDivident() == 0){
			return "No dividend available. P/E ratio can not be calculated!!!";
		}
		Double peRatio = input.getPrice()/input.getStock().getLastDivident();
		logger.info("P/E ratio for stock "+input.getStock().getStockName()+" is "+peRatio);
		return "\nP/E ratio of Stock "
				+input.getStock().getStockName()+" for the price "+input.getPrice()+" is "+peRatio;
	}

	private void ValidateInput() throws Exception {

		if(input == null){
			logger.error("Input is null...");
			throw new NullPointerException("Input not present !!!");
		}if(input.getStock() == null){
			logger.error("Stock is null...");
			throw new NullPointerException("Stock not present !!!");
		}if(input.getPrice() == null || 1>input.getPrice()){
			logger.error("Price is null or less than 1...");
			throw new IllegalArgumentException("Invalid price !!!");
		}
	}
}