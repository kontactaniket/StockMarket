package com.jpmc.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jpmc.dao.StockMarketDao;
import com.jpmc.dto.Input;
import com.jpmc.dto.Output;
import com.jpmc.persistence.Stock;
import com.jpmc.service.factory.OperationStrategyFactory;
import com.jpmc.service.strategy.abs.Strategy;

/**
 * @author Aniket Kulkarni
 * This class is responsible for Deciding the Strategy through Strategy Factory
 * and call the 'performOperation' on the Strategy 
 */
@Service
public class StockMarketService {

	private final static Logger logger = Logger.getLogger(StockMarketService.class);
	
	@Autowired
	@Qualifier("StockMarketDao")
	private StockMarketDao dao;
	
	@Autowired
	@Qualifier("OperationStrategyFactory")
	private OperationStrategyFactory strategyFactory;
	
	/**
	 * Returns Stock object for given stock name.
	 * Throws exception if map is empty or Stock not found for given name.
	 * @param stockName
	 * @return Stock
	 * @throws Exception
	 */
	public Stock getStock(String stockName) throws Exception{

		logger.debug("Calling DAO to fetch the Stock for stock name...");
		return dao.getStock(stockName);
	}

	/**
	 * This method refers to the Strategy Factory to instantiate a Strategy based on input.
	 * After the factory returns the Strategy, call to performOperation() performs the required 
	 * operation and produces the desired OutPut which is sent back to Controller. 
	 * @param input
	 * @return Output
	 * @throws Exception
	 */
	public Output performOperation(Input input) throws Exception {
		
		logger.debug("Calling Factory to decide the Strategy...");
		Strategy strategy = strategyFactory.getStrategy(input);
		return strategy.performOperation();
	}
}