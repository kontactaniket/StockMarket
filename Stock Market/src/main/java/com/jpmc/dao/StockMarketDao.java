package com.jpmc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jpmc.persistence.Stock;
import com.jpmc.persistence.Trade;

/**
 * @author Aniket Kulkarni
 * This class add, holds and fetched data.
 */
@Repository
public class StockMarketDao {

	private final static Logger logger = Logger.getLogger(StockMarketDao.class);
	
	private Map<String, Stock> stockMap;
	private Map<String, List<Trade>> tradeMap;
	
	/**
	 * Initialise the Map with the pre-defined values.
	 * @param map 
	 */
	public void populateStocks(Map<String, Stock> map) {
		this.stockMap = map;
	}

	/**
	 * This method returns Stock from pre-populated Stock map. 
	 * @param stockName
	 * @return Stock
	 * @throws Exception
	 */
	public Stock getStock(String stockName) throws IllegalArgumentException {
		
		logger.debug("Fetching Stock for stock name : "+stockName);
		if(stockMap == null || !stockMap.containsKey(stockName)){
			logger.info("Stock Map is null or stock do not exists for name : "+stockName);	
			throw new IllegalArgumentException("Stock "+stockName+" do not exists!!!");
		}
		return stockMap.get(stockName);
	}

	/**
	 * Records a new trade for a stock.
	 * @param Trade
	 */
	public void recordTrade(Trade trade) {

		logger.debug("Adding a new trade for stock..."+trade.getStockSymbol());
		if(tradeMap == null){
			logger.debug("No trade exists for any stock...");
			tradeMap = new HashMap<String, List<Trade>>();
		}
		List<Trade> trades = tradeMap.get(trade.getStockSymbol());
		if(trades == null){
			logger.debug("No trade exists for stock "+trade.getStockSymbol());
			trades = new ArrayList<Trade>();
			tradeMap.put(trade.getStockSymbol(), trades);
		}
		trades.add(trade);
		logger.debug("Trade added succesfully for stock "+trade.getStockSymbol());
	}

	/**
	 * This methods returns list of Trades for a particular stock
	 * @param stock
	 * @return List<Trade>
	 * @throws Exception
	 */
	public List<Trade> getAllTradesForStock(Stock stock) throws Exception {
		
		logger.debug("Returning the Trade list for stock : "+stock.getStockName());
		if(tradeMap == null || !tradeMap.containsKey(stock.getStockName())){
			logger.info("Trade map is null or trade recorded for stock "+stock.getStockName());
			throw new IllegalArgumentException("No trade recorded for stock "+stock.getStockName());
		}
		return tradeMap.get(stock.getStockName());
	}

	/**
	 * Returns trades of all the stocks of GBCE.
	 * @return List<Trade>
	 * @throws Exception
	 */
	public List<Trade> getAllTradesForGBCE() throws Exception {

		logger.debug("Returning all the trades for GBCE...");
		List<Trade> trades = null;
		if(tradeMap == null || tradeMap.isEmpty()){
			logger.info("Trade map is null or empty...");
			throw new Exception("No trade recorded for GBCE !!!");
		}
		trades = new ArrayList<Trade>();
		for(List<Trade> list : tradeMap.values()){
			trades.addAll(list); 
		}
		logger.debug("Total trades for GBCE : "+trades.size());
		return trades;
	}	
}