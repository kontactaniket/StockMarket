package com.jpmc.service.strategy;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

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
import com.jpmc.persistence.Stock;
import com.jpmc.service.strategy.impl.PeRatioCalculatorStrategy;
import com.jpmc.util.TestData;

/**
 * @author Aniket Kulkarni
 * Tests the PeRatioCalculatorStrategy.java
 */
@RunWith(MockitoJUnitRunner.class)
public class PeRatioCalculatorStrategyTest {

	@InjectMocks
	private PeRatioCalculatorStrategy strategy;
	
	@Mock
	private StockMarketDao dao;
	
    @Before
    public void setupTests() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
	public void testPerformOperationForValidInput() throws Exception{

    	Collection<Stock> stocks = TestData.getAllStockMap().values();
    	for(Stock stock : stocks){
			Input input = new Input(
					OperationTypeEnum.CALCULATEPERATIO, stock, 100.0);
			Output expectedResult = new Output(generateOutput(input));
			strategy.setInput(input);
			assertEquals(
					"Expected result is "+expectedResult, expectedResult, strategy.performOperation());
    	}
	}

	@Test(expected = Exception.class)
	public void testPerformOperationForNullInput() throws Exception {

		strategy.setInput(null);
		strategy.performOperation();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPerformOperationForInvalidPrice() throws Exception {

		strategy.setInput(new Input(
				OperationTypeEnum.CALCULATEPERATIO, TestData.getStockByName("GIN"), -100.0));
		strategy.performOperation();
	}
	
	@Test(expected = NullPointerException.class)
	public void testPerformOperationForNullStock() throws Exception {

		strategy.setInput(new Input(
				OperationTypeEnum.CALCULATEPERATIO, null, 100.0));
		strategy.performOperation();
	}

	private String generateOutput(Input input) {

		if(input.getStock().getLastDivident() == null || input.getStock().getLastDivident() == 0){
			return "No dividend available. P/E ratio can not be calculated!!!";
		}
		Double peRatio = input.getPrice()/input.getStock().getLastDivident();
		return "\nP/E ratio of Stock "
				+input.getStock().getStockName()+" for the price "+input.getPrice()+" is "+peRatio;
	}
}