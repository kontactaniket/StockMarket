package com.jpmc.service.strategy.impl;

import org.apache.log4j.Logger;

import com.jpmc.dto.Input;
import com.jpmc.dto.Output;
import com.jpmc.service.strategy.abs.Strategy;

/**
 * @author Aniket Kulkarni
 * This class is Strategy to calculate Index for calculating the Dividend
 */
public class DivedentCalculatorStrategy implements Strategy {

	private final static Logger logger = Logger.getLogger(DivedentCalculatorStrategy.class);
	private Input input; 
	
	/* (non-Javadoc)
	 * @see com.jpmc.service.strategy.abs.Strategy#setInput(com.jpmc.dto.Input)
	 */
	@Override
	public void setInput(Input input) {
		this.input = input;
	}

	/**
	 * Calculate the dividend for the stock and price entered by user.
	 * Formula to calculate the dividend is ((Stock Fixed Dividend)*(Stock par value)/(Price entered)).
	 * Stock fixed dividend and par value is pre-populated. Price is entered by user.
	 * Returns the result String to be displayed on console.
	 * Validations done on user input for Stock name and price.
	 * @return Output 
	 * @throws NullPointerException, IllegalArgumentException
	 */
	@Override
	public Output performOperation() 
			throws NullPointerException, IllegalArgumentException {
		
		logger.debug("Calculating dividend...");
		ValidateInput();
		String result = getCommonDividentYieldResult()+getPreferredDividentResult();
		logger.info("Dividend calculated  : "+result);
		return new Output(result);
	}

	private String getCommonDividentYieldResult() {
		logger.debug("Calculating common dividend...");
		Double commonDivident = input.getStock().getLastDivident()/input.getPrice();
		logger.info("Common dividend : "+commonDivident);
		return "\nCommon Divident for Stock "
				+input.getStock().getStockName()+" for the price "+input.getPrice()+" is "+commonDivident;
	}

	private String getPreferredDividentResult() {
		logger.debug("Calculating preffered dividend...");
		Double preferredDivident = 
				(input.getStock().getFixedDivident()*input.getStock().getParValue())/input.getPrice();
		logger.info("Preffered dividend : "+preferredDivident);
		return "\nPreferred Divident of Stock "
				+input.getStock().getStockName()+" for the price "+input.getPrice()+" is "+preferredDivident;
	}

	private void ValidateInput() throws NullPointerException, IllegalArgumentException {
		
		if(input == null){
			logger.error("Input is Null...");
			throw new NullPointerException("Invalid input!!!");
		}if(input.getStock() == null){
			logger.error("Stock is Null...");
			throw new NullPointerException("Stock not present!!!");
		}if(input.getPrice() == null || 1>input.getPrice()){
			logger.error("Price is Null or less than 1...");
			throw new IllegalArgumentException("Invalid price!!!");
		}
	}
}