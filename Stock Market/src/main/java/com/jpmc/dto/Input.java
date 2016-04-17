package com.jpmc.dto;

import java.sql.Timestamp;
import com.jpmc.persistence.Stock;

/**
 * @author Aniket Kulkarni
 * This class contains all the input data like Stock, price etc...
 */
public class Input {
	
	private OperationTypeEnum operationType;
	private Stock stock;
	private Double price;
	private Timestamp tradeDt;
	private Double tradedPrice;
	private Integer sharesQuantity;
	private Boolean isBuy;
	
	public Input(OperationTypeEnum operationType, Stock stock,
			Double tradedPrice, Integer sharesQuantity, Boolean isBuy) {

		this.operationType = operationType;
		this.stock = stock;
		this.tradeDt = getCurrentTimestamp();
		this.tradedPrice = tradedPrice;
		this.sharesQuantity = sharesQuantity;
		this.isBuy = isBuy;
	}

	public Input(OperationTypeEnum operationType, Stock stock, Double price) {

		this.operationType = operationType;
		this.stock = stock;
		this.price = price;
	}

	public Input(OperationTypeEnum oprationType, Stock stock) {

		this.operationType = oprationType;
		this.stock = stock;
	}

	public Input(OperationTypeEnum operationType) {

		this.operationType = operationType;
	}

	public OperationTypeEnum getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationTypeEnum operationType) {
		this.operationType = operationType;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Timestamp getTradeDt() {
		return tradeDt;
	}

	public void setTradeDt(Timestamp tradeDt) {
		this.tradeDt = tradeDt;
	}

	public Double getTradedPrice() {
		return tradedPrice;
	}

	public void setTradedPrice(Double tradedPrice) {
		this.tradedPrice = tradedPrice;
	}

	public Integer getSharesQuantity() {
		return sharesQuantity;
	}

	public void setSharesQuantity(Integer sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}

	public Boolean getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Boolean isBuy) {
		this.isBuy = isBuy;
	}

	private Timestamp getCurrentTimestamp() {
		
		java.util.Date date= new java.util.Date();
		return new Timestamp(date.getTime());
	}
}