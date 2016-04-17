package com.jpmc.service.strategy.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jpmc.dao.StockMarketDao;
import com.jpmc.dto.Input;
import com.jpmc.dto.Output;
import com.jpmc.persistence.Trade;
import com.jpmc.service.strategy.abs.Strategy;

/**
 * @author Aniket Kulkarni
 * This class is Strategy to calculate Volume Weighted Stock price.
 */
public class VolWeightedStockPriceStrategy implements Strategy {
	
	private final static Logger logger = Logger.getLogger(VolWeightedStockPriceStrategy.class);
	
	@Autowired
	@Qualifier("StockMarketDao")
	private StockMarketDao dao;
	
	private Input input; 
	
	public VolWeightedStockPriceStrategy() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.jpmc.service.strategy.abs.Strategy#setInput(com.jpmc.dto.Input)
	 */
	@Override
	public void setInput(Input input) {
		this.input = input;
	}

	/** 
	 * Calculates Volume weighted stock price for trades of last 15 mins.
	 * Formula to calculate is ∑(TP*SQ)/SQ
	 * TP is Traded price, SQ is Share Quantity of a trade.
	 * Returns the result String to be displayed on console.
	 * Validations done on user input for Stock name, 
	 * @return Output 
	 * @throws NullPointerException
	 */
	@Override
	public Output performOperation() throws Exception {

		Double priceQuantity = 0D;
		Integer shareQuantity = 0;
		ValidateInput();
		
		/* Get the timestamp which is less than 15 mins for current time.*/
		Timestamp requiredTS = getTS(); 
		List<Trade> trades = dao.getAllTradesForStock(input.getStock());
		logger.debug("Number of trades received for stock "+input.getStock().getStockName()+" is "+trades.size());
		
		/* Get the summation of ((Trade Price)*(Share Quantity)) for all the received trades. */
		for(Trade trade : trades){
			if(!trade.getCreatedDt().before(requiredTS)){
				shareQuantity+=trade.getShareQuantity();
				priceQuantity+=(trade.getTradedPrice()*trade.getShareQuantity());
			}
		} if(shareQuantity == 0) {
			logger.info("Share quantity is 0...");
			return new Output ("No trades avalable for last 15 mins.");
		}
		logger.info("Volume weighted stock price for Stock "
				+input.getStock().getStockName()+" in last 15 mins is "+priceQuantity/shareQuantity);
		return new Output("Volume weighted stock price for Stock "
				+input.getStock().getStockName()+" in last 15 mins is "+priceQuantity/shareQuantity);
	}

	private Timestamp getTS() {
	
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		cal.add(Calendar.MINUTE, -15);
		return new Timestamp(cal.getTimeInMillis());
	}

	private void ValidateInput() throws Exception {

		if(input.getStock() == null){
			throw new NullPointerException("Stock not present !!!");
		}
	}
}