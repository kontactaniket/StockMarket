package com.jpmc.service.strategy.abs;

import com.jpmc.dto.Input;
import com.jpmc.dto.Output;

/**
 * @author Aniket Kulkarni
 * This class implements the Strategy Design Pattern.
 * Implementers of this class provides a strategy to perform an operation.
 */
public interface Strategy {

	/**
	 * This sets the Input data captured from user in Strategy
	 * @param input
	 */
	public void setInput(Input input);
	
	/**
	 * This is the actual operation done by Strategy.
	 * @return
	 * @throws Exception
	 */
	public Output performOperation() throws Exception;
}