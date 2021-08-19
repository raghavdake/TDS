package com.tds.dto;

import java.math.BigDecimal;

public class Valuation {
	private long tradeId;
	private BigDecimal UQL_OC_MMB_MS;
	private BigDecimal UQL_OC_MMB_MS_PC;
	public long getTradeId() {
		return tradeId;
	}
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	public BigDecimal getUQL_OC_MMB_MS() {
		return UQL_OC_MMB_MS;
	}
	public void setUQL_OC_MMB_MS(BigDecimal uQL_OC_MMB_MS) {
		UQL_OC_MMB_MS = uQL_OC_MMB_MS;
	}
	public BigDecimal getUQL_OC_MMB_MS_PC() {
		return UQL_OC_MMB_MS_PC;
	}
	public void setUQL_OC_MMB_MS_PC(BigDecimal uQL_OC_MMB_MS_PC) {
		UQL_OC_MMB_MS_PC = uQL_OC_MMB_MS_PC;
	}
	
}
