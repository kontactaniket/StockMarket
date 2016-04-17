package com.jpmc.persistence;

import java.sql.Timestamp;

/**
 * @author Aniket Kulkarni
 * This class stored Trade data for a Stock.
 */
public class Trade {

	private String stockSymbol;
	private Timestamp createdDt;
	private Integer shareQuantity;
	private Boolean isBuy;
	private Double tradedPrice;
	
	public Trade(String stockSymbol, Timestamp createdDt,
			Integer shareQuantity, Boolean isBuy, Double tradedPrice) {

		this.stockSymbol = stockSymbol;
		this.createdDt = createdDt;
		this.shareQuantity = shareQuantity;
		this.isBuy = isBuy;
		this.tradedPrice = tradedPrice;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public Timestamp getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}
	public Integer getShareQuantity() {
		return shareQuantity;
	}
	public void setShareQuantity(Integer shareQuantity) {
		this.shareQuantity = shareQuantity;
	}
	public Boolean getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(Boolean isBuy) {
		this.isBuy = isBuy;
	}
	public Double getTradedPrice() {
		return tradedPrice;
	}
	public void setTradedPrice(Double tradedPrice) {
		this.tradedPrice = tradedPrice;
	}	
}