package com.jpmc.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Calendar;
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
import com.jpmc.service.strategy.impl.VolWeightedStockPriceStrategy;
import com.jpmc.util.TestData;

/**
 * @author Aniket Kulkarni
 * Tests VolWeightedStockPriceStrategy.java
 */
@RunWith(MockitoJUnitRunner.class)
public class VolWeightedStockPriceStrategyTest {

	@InjectMocks
	private VolWeightedStockPriceStrategy strategy;
	
	@Mock
	private StockMarketDao dao;
	
    @Before
    public void setupTests() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
	public void testPerformOperationForValidInput() throws Exception{

        when(dao.getAllTradesForStock(TestData.getStockByName(
        		"GIN"))).thenReturn(TestData.getTradeListForStock("GIN"));
		Input input = new Input(
				OperationTypeEnum.CALCULATEPERATIO, TestData.getStockByName("GIN"), 100.0);
		Output expectedResult = generateOutput(input);
		strategy.setInput(input);
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, strategy.performOperation());
	}

	@Test(expected = NullPointerException.class)
	public void testPerformOperationForNullStock() throws Exception {

		strategy.setInput(new Input(
				OperationTypeEnum.CALCULATEPERATIO, null, 100.0));
		strategy.performOperation();
	}
	
	private Output generateOutput(Input input) throws Exception {

		Double priceQuantity = 0D;
		Integer shareQuantity = 0;
		Timestamp requiredTS = getTS(); 
		List<Trade> trades = dao.getAllTradesForStock(input.getStock());
		for(Trade trade : trades){
			if(!trade.getCreatedDt().before(requiredTS)){
				shareQuantity+=trade.getShareQuantity();
				priceQuantity+=(trade.getTradedPrice()*trade.getShareQuantity());
			}
		} 
		return new Output("Volume weighted stock price for Stock "
				+input.getStock().getStockName()+" in last 15 mins is "+priceQuantity/shareQuantity);
	}
	
	private Timestamp getTS() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		cal.add(Calendar.MINUTE, -15);
		return new Timestamp(cal.getTimeInMillis());
	}
}