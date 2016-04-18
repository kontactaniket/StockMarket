package com.jpmc.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.jpmc.dao.StockMarketDao;
import com.jpmc.dto.Input;
import com.jpmc.dto.OperationTypeEnum;
import com.jpmc.dto.Output;
import com.jpmc.persistence.Trade;
import com.jpmc.service.strategy.impl.IdexCalculationStrategy;
import com.jpmc.util.TestData;

/**
 * @author Aniket Kulkarni
 * Tests the IdexCalculationStrategy.java
 */
@RunWith(MockitoJUnitRunner.class)
public class IdexCalculationStrategyTest {

	@InjectMocks
	private IdexCalculationStrategy strategy;
	
	@Mock
	private StockMarketDao dao;
	
    @Before
    public void setupTests() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void testPerformOperationForValidInput() throws Exception{

        when(dao.getAllTradesForGBCE()).thenReturn(TestData.getAllTradeList());
		strategy.setInput(new Input(OperationTypeEnum.ALLSHAREINDEX));
		Output expectedResult = getResultForGBCE(TestData.getAllTradeList());
		Output actualResult = strategy.performOperation();
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, actualResult);
		strategy.setInput(null);
	}

	@Test
	public void testPerformOperationForEmptyTradeList() throws Exception{

        when(dao.getAllTradesForGBCE()).thenReturn(TestData.getAllTradeList());
		strategy.setInput(new Input(OperationTypeEnum.ALLSHAREINDEX));
		Output expectedResult = getResultForGBCE(new ArrayList<Trade>());
		Output actualResult = strategy.performOperation();
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, actualResult);
		strategy.setInput(null);
	}
	
	private Output getResultForGBCE(List<Trade> tradesList) {

		Integer tradeCount = 0;
		Double tradePrice = 0.0;
		List<Trade> trades = TestData.getAllTradeList();
		for(Trade trade : trades){
			tradePrice+=trade.getTradedPrice();
			tradeCount++;
		}
		if(tradeCount == 0){
			return new Output("No trade recorded for GBCE.");
		}
		return new Output("GBCE All Share Index is "+Math.pow(tradePrice, (1D/tradeCount)));
	}
}