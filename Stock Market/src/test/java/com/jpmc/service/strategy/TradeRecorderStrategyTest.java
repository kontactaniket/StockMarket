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
import com.jpmc.service.strategy.impl.TradeRecorderStrategy;
import com.jpmc.util.TestData;

/**
 * @author Aniket Kulkarni
 * Tests TradeRecorderStrategy.java
 */
@RunWith(MockitoJUnitRunner.class)
public class TradeRecorderStrategyTest {

	@InjectMocks
	private TradeRecorderStrategy strategy;
	
	@Mock
	private StockMarketDao dao;
	
    @Before
    public void setupTests() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
	public void testPerformOperationForValidInput() throws Exception{

		Input input = new Input(OperationTypeEnum.RECORDTRADE, 
				TestData.getStockByName("GIN"), 1000D, 10, true);
		Output expectedResult = generateOutput(input);
		strategy.setInput(input);
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, strategy.performOperation());
	}

	@Test(expected = NullPointerException.class)
	public void testPerformOperationForNullStock() throws Exception {

		strategy.setInput(new Input(
				OperationTypeEnum.RECORDTRADE, null, 100.0));
		strategy.performOperation();
	}

	@Test(expected = Exception.class)
	public void testPerformOperationForInvalidShareQuantity() throws Exception {

		strategy.setInput(new Input(OperationTypeEnum.RECORDTRADE, 
				TestData.getStockByName("GIN"), 1000D, -10, true));
		strategy.performOperation();
	}

	@Test(expected = Exception.class)
	public void testPerformOperationForNullShareQuantity() throws Exception {

		strategy.setInput(new Input(OperationTypeEnum.RECORDTRADE, 
				TestData.getStockByName("GIN"), 1000D, null, true));
		strategy.performOperation();
	}

	@Test(expected = NullPointerException.class)
	public void testPerformOperationForNullBuySellIndicator() throws Exception {

		strategy.setInput(new Input(OperationTypeEnum.RECORDTRADE, 
				TestData.getStockByName("GIN"), 1000D, 10, null));
		strategy.performOperation();
	}

	@Test(expected = Exception.class)
	public void testPerformOperationForInavlidTradedPrice() throws Exception {

		strategy.setInput(new Input(OperationTypeEnum.RECORDTRADE, 
				TestData.getStockByName("GIN"), -1000D, 10, true));
		strategy.performOperation();
	}

	@Test(expected = Exception.class)
	public void testPerformOperationForNullTradedPrice() throws Exception {

		strategy.setInput(new Input(OperationTypeEnum.RECORDTRADE, 
				TestData.getStockByName("GIN"), null, 10, true));
		strategy.performOperation();
	}

	private Output generateOutput(Input input) throws Exception {

		return new Output("Trade added successfully.");
	}
}