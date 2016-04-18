package com.jpmc.persistence;

import com.jpmc.dto.StockTypeEnum;

/**
 * @author Aniket Kulkarni
 * This class stored data for a Stock.
 */
public class Stock {

	private String stockName;
	private StockTypeEnum stockType; 
	private Double lastDivident;
	private Double fixedDivident;
	private Double parValue;
	
	public Stock(String stockName, StockTypeEnum stockType, Double lastDivident,
			Double fixedDivident, Double parValue) {
		
		this.stockName = stockName;
		this.stockType = stockType;
		this.lastDivident = lastDivident;
		this.fixedDivident = fixedDivident;
		this.parValue = parValue;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public StockTypeEnum getStockType() {
		return stockType;
	}

	public void setStockType(StockTypeEnum stockType) {
		this.stockType = stockType;
	}

	public Double getLastDivident() {
		return lastDivident;
	}

	public void setLastDivident(Double lastDivident) {
		this.lastDivident = lastDivident;
	}

	public Double getFixedDivident() {
		return fixedDivident;
	}

	public void setFixedDivident(Double fixedDivident) {
		this.fixedDivident = fixedDivident;
	}

	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (fixedDivident == null) {
			if (other.fixedDivident != null)
				return false;
		} else if (!fixedDivident.equals(other.fixedDivident))
			return false;
		if (lastDivident == null) {
			if (other.lastDivident != null)
				return false;
		} else if (!lastDivident.equals(other.lastDivident))
			return false;
		if (parValue == null) {
			if (other.parValue != null)
				return false;
		} else if (!parValue.equals(other.parValue))
			return false;
		if (stockName == null) {
			if (other.stockName != null)
				return false;
		} else if (!stockName.equals(other.stockName))
			return false;
		if (stockType != other.stockType)
			return false;
		return true;
	}
}