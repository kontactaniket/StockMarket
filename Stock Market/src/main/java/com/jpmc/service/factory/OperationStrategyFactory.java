package com.jpmc.service.factory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jpmc.dto.Input;
import com.jpmc.dto.OperationTypeEnum;
import com.jpmc.service.strategy.abs.Strategy;
import com.jpmc.service.strategy.impl.DivedentCalculatorStrategy;
import com.jpmc.service.strategy.impl.IdexCalculationStrategy;
import com.jpmc.service.strategy.impl.PeRatioCalculatorStrategy;
import com.jpmc.service.strategy.impl.TradeRecorderStrategy;
import com.jpmc.service.strategy.impl.VolWeightedStockPriceStrategy;

/**
 * @author Aniket Kulkarni
 * This class implements Factory design pattern.  
 */
@Component
public class OperationStrategyFactory {

	private final static Logger logger = Logger.getLogger(OperationStrategyFactory.class);
	
	@Autowired
	private ApplicationContext context;
	
	/**
	 * Returns a strategy for input to perform an operation based on the input.
	 * @param input
	 * @return Strategy
	 */
	public Strategy getStrategy(Input input) {
		
		logger.debug("Deciding the Strategy...");
		Strategy strategy = null;
	
		/* Based on Operation Enum the appropriate Strategy is instantiated. */
		if(input.getOperationType().equals(OperationTypeEnum.CALCULATEDIVIDENTYIELD)){
			strategy = (DivedentCalculatorStrategy)context.getBean("DivedentCalculatorStrategy");
			strategy.setInput(input);
			logger.info("Strategy selected : "+strategy.getClass().getName());
			return strategy;
		} if(input.getOperationType().equals(OperationTypeEnum.CALCULATEPERATIO)){
			strategy = (PeRatioCalculatorStrategy)context.getBean("PeRatioCalculatorStrategy");
			strategy.setInput(input);
			logger.info("Strategy selected : "+strategy.getClass().getName());
			return strategy;
		} if(input.getOperationType().equals(OperationTypeEnum.RECORDTRADE)){
			strategy = (TradeRecorderStrategy)context.getBean("TradeRecorderStrategy");
			strategy.setInput(input);
			logger.info("Strategy selected : "+strategy.getClass().getName());
			return strategy;
		} if(input.getOperationType().equals(OperationTypeEnum.VOLWIEGTEDSTOCKPRICE)){
			strategy = (VolWeightedStockPriceStrategy)context.getBean("VolWeightedStockPriceStrategy");
			strategy.setInput(input);
			logger.info("Strategy selected : "+strategy.getClass().getName());
			return strategy;
		} if(input.getOperationType().equals(OperationTypeEnum.ALLSHAREINDEX)){
			strategy = (IdexCalculationStrategy)context.getBean("AllShareIdexCalculationStrategy");
			strategy.setInput(input);
			logger.info("Strategy selected : "+strategy.getClass().getName());
			return strategy;
		}
		logger.error("No Strategy selected for given Operation Type : "+input.getOperationType().name());
		return null;
	}
}