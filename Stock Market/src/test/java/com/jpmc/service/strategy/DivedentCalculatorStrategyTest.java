package com.jpmc.service.strategy;

import static org.junit.Assert.assertEquals;

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
import com.jpmc.service.strategy.impl.DivedentCalculatorStrategy;
import com.jpmc.util.TestData;

/**
 * @author Aniket Kulkarni
 * Tests the DivedentCalculatorStrategy.java
 */
@RunWith(MockitoJUnitRunner.class)
public class DivedentCalculatorStrategyTest {

	@InjectMocks
	private DivedentCalculatorStrategy strategy;
	
	@Mock
	private StockMarketDao dao;
	
    @Before
    public void setupTests() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
	public void testPerformOperationForValidInput() throws Exception{

		Input input = new Input(
				OperationTypeEnum.CALCULATEDIVIDENTYIELD, TestData.getStockByName("GIN"), 100.0);
		Output expectedResult = new Output(generateOutput(input));
		strategy.setInput(input);
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, strategy.performOperation());
	}

	@Test(expected = NullPointerException.class)
	public void testPerformOperationForNullInput() throws Exception {

		strategy.setInput(null);
		strategy.performOperation();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPerformOperationForInvalidPrice() throws Exception {

		strategy.setInput(new Input(
				OperationTypeEnum.CALCULATEDIVIDENTYIELD, TestData.getStockByName("GIN"), -100.0));
		strategy.performOperation();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPerformOperationForNullPrice() throws Exception {

		strategy.setInput(new Input(
				OperationTypeEnum.CALCULATEDIVIDENTYIELD, TestData.getStockByName("GIN"), null));
		strategy.performOperation();
	}

	@Test(expected = NullPointerException.class)
	public void testPerformOperationForNullStock() throws Exception {

		strategy.setInput(new Input(
				OperationTypeEnum.CALCULATEDIVIDENTYIELD, null, 100.0));
		strategy.performOperation();
	}
	
	private String generateOutput(Input input) {

		return getCommonDividentYieldResult(input)+getPreferredDividentResult(input);
	}
	
	private String getCommonDividentYieldResult(Input input) {
		Double commonDivident = input.getStock().getLastDivident()/input.getPrice();
		return "\nCommon Divident for Stock "
				+input.getStock().getStockName()+" for the price "+input.getPrice()+" is "+commonDivident;
	}

	private String getPreferredDividentResult(Input input) {
		Double preferredDivident = 
				(input.getStock().getFixedDivident()*input.getStock().getParValue())/input.getPrice();
		return "\nPreferred Divident of Stock "
				+input.getStock().getStockName()+" for the price "+input.getPrice()+" is "+preferredDivident;
	}
}