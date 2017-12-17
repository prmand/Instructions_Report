package com.instruction.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InstructionData {

	private String entity;
	private String buyOrSellFlag;
	private BigDecimal agreedFix;
	private String currency;
	private LocalDate instructionDate;
	private LocalDate settlementDate;
	private int units;
	private BigDecimal ppu;
	private BigDecimal tradeAmount;
	private int rank;

	public InstructionData(String entity, String buyOrSellFlag, BigDecimal agreedFix, String currency, LocalDate instructionDate,
			LocalDate settlementDate, int units, BigDecimal ppu) {
		this.entity = entity;
		this.buyOrSellFlag = buyOrSellFlag;
		this.agreedFix = agreedFix;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.ppu = ppu;
		this.tradeAmount=calculateTradeAmount(this);
	}
	private BigDecimal calculateTradeAmount(InstructionData instruction) {
	BigDecimal tradeAmount = instruction.getPpu().multiply(instruction.getAgreedFix())
			.multiply(BigDecimal.valueOf(instruction.getUnits()));
	instruction.setTradeAmount(tradeAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN));
	return tradeAmount;
}
	public int getRank() {
		return rank;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public String getEntity() {
		return entity;
	}

	public String getBuyOrSellFlag() {
		return buyOrSellFlag;
	}

	public BigDecimal getAgreedFix() {
		return agreedFix;
	}

	public String getCurrency() {
		return currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public int getUnits() {
		return units;
	}

	public BigDecimal getPpu() {
		return ppu;
	}
	
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
