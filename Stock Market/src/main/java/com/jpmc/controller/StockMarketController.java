package com.jpmc.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.jpmc.dto.Input;
import com.jpmc.dto.OperationTypeEnum;
import com.jpmc.dto.Output;
import com.jpmc.persistence.Stock;
import com.jpmc.service.StockMarketService;

/**
 * @author Aniket Kulkarni
 * This class is responsible to capture the user input from console and send it to service.
 */
@Controller
public class StockMarketController {

	private final static Logger logger = Logger.getLogger(StockMarketController.class);
	
	@Autowired
	@Qualifier("StockMarketService")
	private StockMarketService service;
	
	@Autowired
	private Scanner scanner;
	
	/**
	 * This method provides the user with options to choose to perform an operation. 
	 */
	public void captureInput() {
	
		logger.debug("Capturing user input...");
		
		Integer number = 0;
		while(number!=6){
			/* Capture the user action by selecting a number between 1 & 7.
			 * Control will remain in loop until user selects 7 to exit.
			 */
			System.out.println("\n\nChoose options from below --");
			System.out.println("Enter 1 to calculate the divident yeild");
			System.out.println("Enter 2 to calculate the P/E ratio");
			System.out.println("Enter 3 to record a trade");
			System.out.println("Enter 4 to calculate Volume wighted Stock Price");
			System.out.println("Enter 5 to calculate all share index for GBCE");
			System.out.println("Enter 6 to exit");
			System.out.println("Enter the value");
			
			try{
				number = new Integer(scanner.next());
				if(1>number || number>6){
					logger.info("Invalid entry : "+number);
					throw new Exception("Please enter value between 1 and 6");
				}
				Input input = generateInput(number);
				if(input != null){
					Output output = service.performOperation(input);
					System.out.println(output.getResult());
				}
			}catch(InputMismatchException ex){
				logger.error("Wrong Input "+ex);
				System.out.println("Wrong value entered!!!");
			}catch(NumberFormatException ex){
				logger.error("Wrong Input "+ex);
				System.out.println("Wrong value entered!!!");
			}catch(Exception ex){
				logger.error("Wrong Input "+ex);
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("Thank You !!!");
	}

	private Input generateInput(
			int option) throws InputMismatchException, NumberFormatException, Exception{

		Input input = null;
		switch (option) {
		case 1:
			input = generateInputToCalculateDividentYield();
			break;
		case 2:
			input = generateInputToCalculatePeRatio();
			break;
		case 3:
			input = generateInputCaptureTrade();
			break;
		case 4:
			input = generateInputToCalculateVolWeightedStockPrice();
			break;
		case 5:
			input = generateAllShareIndexForGBCE();
			break;
		case 6:
			break;
		}
		return input;
	}

	private Input generateAllShareIndexForGBCE() {

	/* All share index will be calculated for pre-propulted GBCE stocks using the prices in the trade entered by users.
	 */
		logger.debug("Creating Input to calculate Index for all the shares of GBCE...");
		return new Input(OperationTypeEnum.ALLSHAREINDEX);		
	}

	private Input generateInputToCalculateVolWeightedStockPrice() throws Exception {

	/* Volume Weighted Stock price will be calculated for trades recorded in last 15 mins of a pre-propulted stock only.
	 * The stock name is accepted from the user.
	 */
		logger.debug("Capturing input for Volume Weighted Stock Price...");
		System.out.println("Enter the stock name : ");
		Stock stock = service.getStock(scanner.next());
		logger.debug("Creating Input to Volume Weighted Stock Price...");
		return new Input(OperationTypeEnum.VOLWIEGTEDSTOCKPRICE, stock);
	}

	private Input generateInputCaptureTrade() throws Exception {

	/* Trade will be recorded for a pre-propulted stock only.
	 * The stock name, Buy/Sell indicator, Share quantity and trade price is accepted from the user.
	 */
		logger.debug("Capturing input to record trade...");
		System.out.println("Enter the stock name : ");
		Stock stock = service.getStock(scanner.next());
		System.out.println("Enter 1 to BUY and 2 to SELL: ");
		Integer buySellIndicator = new Integer(scanner.next());
		if(1>buySellIndicator || buySellIndicator>2){
			logger.error("Wrong Input for Buy/Sell indicator : "+buySellIndicator);
			throw new Exception("Invalid input !!! ");
		}
		System.out.println("Enter share quantity : ");
		Integer shareQuantity = new Integer(scanner.next());
		if(0>shareQuantity){
			logger.error("Wrong Input for Share Quantity : "+shareQuantity);
			throw new Exception("Invalid share quantity !!! ");
		}
		System.out.println("Enter the trade price : ");
		Double tradePrice = new Double(scanner.next());
		if(0>tradePrice){
			logger.error("Wrong Input for Price : "+tradePrice);
			throw new Exception("Invalid trade price !!!");
		}
		logger.debug("Creating Input record trade...");
		return new Input(OperationTypeEnum.RECORDTRADE, stock, 
				tradePrice, shareQuantity, buySellIndicator==1?true:false);
	}

	private Input generateInputToCalculatePeRatio() throws Exception {


	/* P/E ratio is calculated for a pre-propulted stock only.
	 * The stock name and price for which P/E ratio to be calculated is accepted from the user.
	 */
		logger.debug("Capturing input to calculate P/E ratio...");
		System.out.println("Enter the stock name : ");
		Stock stock = service.getStock(scanner.next());
		System.out.println("Enter the price : ");
		logger.debug("Creating Input for Calculate P/E ration...");
		return new Input(OperationTypeEnum.CALCULATEPERATIO, stock, new Double(scanner.next()));
	}

	private Input generateInputToCalculateDividentYield() throws Exception {

	/* Dividend is calculated for a pre-propulted stock only.
	 * The stock name and price for which dividend to be calculated is accepted from the user.
	 */
		logger.debug("Capturing input to calculate Dividend Yield...");
		System.out.println("Enter the stock name : ");
		Stock stock = service.getStock(scanner.next());
		System.out.println("Enter the price : ");
		logger.debug("Creating Input for Calculate Dividend Yield...");
		return new Input(OperationTypeEnum.CALCULATEDIVIDENTYIELD, stock, new Double(scanner.next()));
	}
}