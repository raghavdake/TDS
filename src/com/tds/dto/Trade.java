package com.tds.dto;

import java.util.Date;

public class Trade {
	private String inventory;
	private String book;
	private String system;
	private String legalEntity; 
	private long tradeId; 
	private int version; 
	private String tradeStatus; 
	private String productType; 
	private String resettingLeg;
 
	private String productSubType;
	private String tDSProductType;
	private String secCodeSubType;
	private String swapType;
	private String description;
	private Date tradeDate;
	private Date startDate;
	private Date maturityDate;
	private String tradeDateStr;
	private String startDateStr;
	private String maturityDateStr;
	
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		book = book;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		system = system;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public long getTradeId() {
		return tradeId;
	}
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getResettingLeg() {
		return resettingLeg;
	}
	public void setResettingLeg(String resettingLeg) {
		this.resettingLeg = resettingLeg;
	}
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}
	public String gettDSProductType() {
		return tDSProductType;
	}
	public void settDSProductType(String tDSProductType) {
		this.tDSProductType = tDSProductType;
	}
	public String getSecCodeSubType() {
		return secCodeSubType;
	}
	public void setSecCodeSubType(String secCodeSubType) {
		this.secCodeSubType = secCodeSubType;
	}
	public String getSwapType() {
		return swapType;
	}
	public void setSwapType(String swapType) {
		this.swapType = swapType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date l) {
		this.tradeDate = l;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getTradeDateStr() {
		return tradeDateStr;
	}
	public void setTradeDateStr(String tradeDateStr) {
		this.tradeDateStr = tradeDateStr;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getMaturityDateStr() {
		return maturityDateStr;
	}
	public void setMaturityDateStr(String maturityDateStr) {
		this.maturityDateStr = maturityDateStr;
	}

}
