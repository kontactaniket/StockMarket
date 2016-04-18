package com.jpmc.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.jpmc.dto.StockTypeEnum;
import com.jpmc.persistence.Stock;
import com.jpmc.persistence.Trade;
import com.jpmc.util.TestData;

/**
 * @author Aniket Kulkarn
 * Tests the StockMarketDao.java  	
 */
@RunWith(MockitoJUnitRunner.class)
public class StockMarketDaoTest {

	StockMarketDao dao = new StockMarketDao();
    
    @Test
	public void testPopulateStocks() {
    	dao.populateStocks(TestData.getAllStockMap());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetStockFromNullMap() throws Exception {

		dao.populateStocks(null);
		dao.getStock("GIN");	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetStockFromInvalidStockName() throws Exception {

		dao.populateStocks(TestData.getAllStockMap());
		dao.getStock("TMP");	
	}
	
	@Test
	public void testGetStockFromValidStockName() throws Exception {

		dao.populateStocks(TestData.getAllStockMap());
		Stock actualResult = dao.getStock("GIN");
		Stock expectedResult = TestData.getStockByName("GIN");
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, actualResult);
	}
	
	@Test
	public void testRecordTradeWhenStockMapIsNull() {

		dao.populateStocks(null);
		dao.recordTrade(TestData.getNewTrade());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAllTradesForStockWhenTradeMapIsNull() throws Exception {

		dao.populateStocks(null);
		dao.getAllTradesForStock(TestData.getStockByName("GIN"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAllTradesForStockForInvalidStock() throws Exception {

		dao.populateStocks(TestData.getAllStockMap());
		dao.getAllTradesForStock(
				new Stock("TMP", StockTypeEnum.Common, new Double(0), null, new Double(100)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetAllTradesForValidStock() throws Exception {

		List<Trade> expectedResult = TestData.getTradeListForStock("GIN");
		dao.populateStocks(TestData.getAllStockMap());
		List<Trade> actualResult = dao.getAllTradesForStock(TestData.getStockByName("GIN"));
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, actualResult);
	}
	
	@Test(expected = Exception.class)
	public void testGetAllTradesForGBCEWhenTradeMapIsNull() throws Exception {

		dao.populateStocks(TestData.getAllStockMap());
		dao.getAllTradesForGBCE();
	}
	
	@Test
	public void testGetAllTradesForGBCEF() throws Exception {

		List<Trade> expectedResult = TestData.getAllTradeList();
		dao.populateStocks(TestData.getAllStockMap());
		for (Trade trade : expectedResult) {
			dao.recordTrade(trade);
		}
		List<Trade> actualResult = dao.getAllTradesForGBCE();
		assertEquals(
				"Expected result is "+expectedResult, expectedResult, actualResult);
	}
}