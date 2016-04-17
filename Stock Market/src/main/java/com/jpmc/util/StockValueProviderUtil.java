package com.jpmc.util;

import java.util.HashMap;
import java.util.Map;

import com.jpmc.dto.StockTypeEnum;
import com.jpmc.persistence.Stock;

/**
 * @author Aniket Kulkarni
 * This class provides data to be pre-populated in application 
 */
public class StockValueProviderUtil {

	private static Map<String, Stock> stockMap = null;
	
	/* Initialise the Stock map with data given in problem statement */
	static{
		stockMap = new HashMap<String, Stock>();
		stockMap.put("TEA", new Stock
				("TEA", StockTypeEnum.Common, new Double(0), null, new Double(100)));
		stockMap.put("POP", new Stock
				("POP", StockTypeEnum.Common, new Double(8), null, new Double(100)));
		stockMap.put("ALE", new Stock
				("ALE", StockTypeEnum.Common, new Double(23), null, new Double(60)));
		stockMap.put("GIN", new Stock
				("GIN", StockTypeEnum.Preffered, new Double(8), new Double(2), new Double(100)));
		stockMap.put("JOE", new Stock
				("JOE", StockTypeEnum.Common, new Double(13), null, new Double(250)));
	}
	
	/**
	 * Returns the whole Stock map initialised while loading the class.  
	 * @return Map<String, Stock>
	 */
	public static Map<String, Stock> getAllStockMap() {
		return stockMap;
	}
	
	/**
	 * Return the Stock for the given stock name.
	 * @param stockName
	 * @return Stock
	 */
	public static Stock getAllStockByName(String stockName) {
		return stockMap.get(stockName);
	}

}
