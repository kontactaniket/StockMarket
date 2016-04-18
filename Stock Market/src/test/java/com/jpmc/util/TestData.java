package com.jpmc.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jpmc.dto.StockTypeEnum;
import com.jpmc.persistence.Stock;
import com.jpmc.persistence.Trade;

/**
 * @author Aniket Kulkarni
 * This holds test data to be used by test cases.	
 */
public class TestData {

	private static Map<String, Stock> stockMap = null;
	private static Map<String, List<Trade>> tradeMap = null;
	
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
		
		tradeMap = new HashMap<String, List<Trade>>();
		tradeMap.put("TEA", getTradeList("TEA"));
		tradeMap.put("POP", getTradeList("POP"));
		tradeMap.put("ALE", getTradeList("ALE"));
		tradeMap.put("GIN", getTradeList("GIN"));
		tradeMap.put("JOE", getTradeList("JOE"));
		
	}
	
	public static Map<String, Stock> getAllStockMap() {
		return stockMap;
	}
	
	public static Stock getStockByName(String stockName) {
		return stockMap.get(stockName);
	}

	public static List<Trade> getAllTradeList(){
		List<Trade> trades = new ArrayList<Trade>();
		Collection<List<Trade>> tradeCollection = tradeMap.values();
		for(List<Trade> tradeList : tradeCollection){
			trades.addAll(tradeList);
		}
		return trades;
	}
	
	public static List<Trade> getTradeListForStock(String stockName){
		return tradeMap.get(stockName);
	}
	
	public static Trade getNewTrade() {
		return populateTrade("GIN", getCurrentTimestamp(), 50, true, 210D);
	}

	private static List<Trade> getTradeList(String stockName) {

		List<Trade> trades = new ArrayList<Trade>();
		if(stockName.equals("TEA")){
			for(int i=1;i<6;i++){
				trades.add(populateTrade(
						"TEA", getCurrentTimestamp(), 10*i, true, 6000D/i));
			}
		}else if(stockName.equals("POP")){
			for(int i=1;i<4;i++){
				trades.add(populateTrade(
						"POP", getCurrentTimestamp(), 8*i, true, 3000D/i));
			}
		}else if(stockName.equals("ALE")){
			for(int i=1;i<2;i++){
				trades.add(populateTrade(
						"ALE", getCurrentTimestamp(), 15*i, true, 800D/i));
			}
		}else if(stockName.equals("GIN")){
			for(int i=1;i<8;i++){
				trades.add(populateTrade(
						"GIN", getCurrentTimestamp(), 10*i, true, 2100D/i));
			}
		}else if(stockName.equals("JOE")){
			for(int i=1;i<4;i++){
				trades.add(populateTrade(
						"JOE", getCurrentTimestamp(), 10*i, true, 1200D/i));
			}
		}
		return trades;
	}
	
	private static Trade populateTrade(String stockSymbol,
			Timestamp currentTimestamp, Integer quantity, boolean isBuy, double tradePrice) {

		return new Trade(stockSymbol, currentTimestamp, quantity, isBuy, tradePrice);
	}
	
	private static Timestamp getCurrentTimestamp() {
		
		java.util.Date date= new java.util.Date();
		return new Timestamp(date.getTime());
	}

	public static String getInvalidInputForCaptureInput() {

		return "Invalid Input";
	}
}