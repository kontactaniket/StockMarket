package com.jpmc.service.strategy.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jpmc.dao.StockMarketDao;
import com.jpmc.dto.Input;
import com.jpmc.dto.Output;
import com.jpmc.persistence.Trade;
import com.jpmc.service.strategy.abs.Strategy;

/**
 * @author Anket Kulkarni
 * This class is Strategy to calculate Index record a trade
 */
@Component
public class TradeRecorderStrategy implements Strategy {

	private final static Logger logger = Logger.getLogger(TradeRecorderStrategy.class);
	
	@Autowired
	@Qualifier("StockMarketDao")
	private StockMarketDao dao;
	
	private Input input; 
	
	public TradeRecorderStrategy() {
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
	 * Records a Trade for Stock with values entered by users.
	 * Returns the result String to be displayed on console.
	 * Validations done on user input for Stock name and share quantity, 
	 * Buy/Sell indicator and Traded price.
	 * @return Output 
	 * @throws NullPointerException, Exception
	 */
	@Override
	public Output performOperation() throws Exception {

		ValidateInput();
		dao.recordTrade(createTrade());
		logger.debug("Trade recorded succesfully...");
		return new Output("Trade added successfully.");
	}

	private Trade createTrade() {
		logger.info("Recording a trade...");
		return new Trade(input.getStock().getStockName(), 
				input.getTradeDt(), input.getSharesQuantity(), input.getIsBuy(), input.getTradedPrice());
	}

	private void ValidateInput() throws Exception {

		if(input == null){
			logger.error("Input is null...");
			throw new NullPointerException("Input not present !!!");
		}if(input.getStock() == null){
			logger.error("Stock is null...");
			throw new NullPointerException("Stock not present !!!");
		}if(input.getSharesQuantity() == null || 1>input.getSharesQuantity()){
			logger.error("Share quantity is null or less than 1...");
			throw new Exception("Invalid Share Quantity !!!");
		}if(input.getIsBuy() == null){
			logger.error("Buy/Sell indicator is null...");
			throw new NullPointerException("Invalid Buy/Sell indicator !!!");
		}if(input.getTradedPrice() == null || 1>input.getTradedPrice()){
			logger.error("Traded price is null or less than 1...");
			throw new Exception("Invalid traded price !!!");
		}
	}
}