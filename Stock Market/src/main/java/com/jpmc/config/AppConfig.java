package com.jpmc.config;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jpmc.controller.StockMarketController;
import com.jpmc.dao.StockMarketDao;
import com.jpmc.service.StockMarketService;
import com.jpmc.service.factory.OperationStrategyFactory;
import com.jpmc.service.strategy.impl.DividentCalculatorStrategy;
import com.jpmc.service.strategy.impl.IdexCalculationStrategy;
import com.jpmc.service.strategy.impl.PeRatioCalculatorStrategy;
import com.jpmc.service.strategy.impl.TradeRecorderStrategy;
import com.jpmc.service.strategy.impl.VolWeightedStockPriceStrategy;
import com.jpmc.util.StockValueProviderUtil;

/**
 * @author Aniket Kulkarni
 * This class is responsible for initiating the bean to be autowired.
 */
@Configuration
@ComponentScan("com.jpmc")
public class AppConfig {

	@Bean(name="StockMarketService")
	public StockMarketService getStockMarketService(){
		return new StockMarketService();
	}
	
	@Bean(name="StockMarketController")
	public StockMarketController getStockMarketController(){
		return new StockMarketController();
	}

	@Bean(name="StockMarketDao")
	public StockMarketDao getStockMarketDao(){
		StockMarketDao dao = new StockMarketDao();
		dao.populateStocks(StockValueProviderUtil.getAllStockMap());
		return dao;
	}
	
	@Bean(name="InputScanner")
	public Scanner getScanner(){
		return new Scanner(System.in);
	}
	
	@Bean(name="DivedentCalculatorStrategy")
	public DividentCalculatorStrategy getDivedentCalculatorStrategy(){
		return new DividentCalculatorStrategy();
	}
	
	@Bean(name="PeRatioCalculatorStrategy")
	public PeRatioCalculatorStrategy getPeRatioCalculatorStrategy(){
		return new PeRatioCalculatorStrategy();
	}
	
	@Bean(name="TradeRecorderStrategy")
	public TradeRecorderStrategy getTradeRecorderStrategy(){
		return new TradeRecorderStrategy();
	}
	
	@Bean(name="VolWeightedStockPriceStrategy")
	public VolWeightedStockPriceStrategy getVolWeightedStockPriceStrategy(){
		return new VolWeightedStockPriceStrategy();
	}
	
	@Bean(name="OperationStrategyFactory")
	public OperationStrategyFactory getOperationStrategyFactory(){
		return new OperationStrategyFactory();
	}
	
	@Bean(name="AllShareIdexCalculationStrategy")
	public IdexCalculationStrategy getAllShareIdexCalculationStrategy(){
		return new IdexCalculationStrategy();
	}
}