package com.jpmc.service.strategy.impl;

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
 * This class is Strategy to calculate Index for pre-populated stocks
 */
public class IdexCalculationStrategy implements Strategy {

	private final static Logger logger = Logger.getLogger(IdexCalculationStrategy.class);
	
	@Autowired
	@Qualifier("StockMarketDao")
	private StockMarketDao dao;
	
	private Input input; 
	
	public IdexCalculationStrategy() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.jpmc.service.strategy.abs.Strategy#setInput(com.jpmc.dto.Input)
	 */
	public void setInput(Input input) {
		this.input = input;
	}

	/** 
	 * Calculates Index for the given stock. 
	 * If stock is not specified then index for all the stocks will be calculated. 
	 * Formula to calculate is Math.pow(X, Y) function. 
	 * X is summation prices of all the trades. Y is inverse of trade count
	 * Returns the result String to be displayed on console.
	 * @return Output 
	 * @throws Exception
	 */
	public Output performOperation() throws Exception {

		Integer tradeCount = 0;
		Double tradePrice = 0.0;
		logger.debug("Calculating Index...");

		/* If user do not specify a stock, fetch trades for all the stocks */
		if(input.getStock() == null){
			logger.debug("Stock not specified. Calculating index for all trades...");

			List<Trade> trades = dao.getAllTradesForGBCE();
			logger.info("Number of trades "+trades.size());
			for(Trade trade : trades){
				tradePrice+=tradePrice+trade.getTradedPrice();
				tradeCount++;
			}
		}
		if(tradeCount == 0){
			logger.info("No trades found for stock "+input.getStock().getStockName());
			return new Output("No trade recorded for GBCE.");
		}
		logger.debug("Calculating the Index...");
		return new Output("GBCE All Share Index is "+Math.pow(tradePrice, (1/tradeCount)));
	 }
}