package com.jpmc.service.strategy;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
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
import com.jpmc.dto.StockTypeEnum;
import com.jpmc.persistence.Stock;
import com.jpmc.service.strategy.impl.DividentCalculatorStrategy;
import com.jpmc.util.TestData;

/**
 * @author Aniket Kulkarni
 * Tests the DivedentCalculatorStrategy.java
 */
@RunWith(MockitoJUnitRunner.class)
public class DividentCalculatorStrategyTest {

	@InjectMocks
	private DividentCalculatorStrategy strategy;
	
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
			Input input = new Input(OperationTypeEnum.CALCULATEDIVIDENTYIELD, stock, 100.0);
			Output expectedResult = new Output(generateOutput(input));
			strategy.setInput(input);
			assertEquals(
					"Expected result is "+expectedResult, expectedResult, strategy.performOperation());
    	}
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

		String result = getCommonDividentYieldResult(input);
		if(input.getStock().getStockType().equals(StockTypeEnum.Preffered)){
			result+=getPreferredDividentResult(input);
		}
		return result;
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