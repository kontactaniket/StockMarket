package com.jpmc.app;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jpmc.config.AppConfig;
import com.jpmc.controller.StockMarketController;

/**
 * @author Aniket Kulkarni
 * This class contains main method which is starting point of StockMarket application
 */
public class App 
{
	private final static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args ){

    	logger.debug("Starting Stock Market Application...");
    	/* Load the Spring context */
    	ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    	StockMarketController controller = (StockMarketController)context.getBean("StockMarketController");
    	controller.captureInput();
    	((ConfigurableApplicationContext)context).close();
    }
}