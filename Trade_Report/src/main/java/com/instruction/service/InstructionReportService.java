package com.instruction.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.instruction.model.InstructionData;

public interface InstructionReportService {
	
	/**
     * This function will provide the report for Amounts in USD settled incoming instructions everyday  
     * @param list iterator with Instructions     
     */
	StringBuilder generateReportForIncoming(List<InstructionData> instructions);
	/**
     * This function will provide the report for Amounts in USD settled outgoing instructions everyday  
     * @param list iterator with Instructions
     */
	StringBuilder generateReportForOutgoing(List<InstructionData> instructions);
	/**
     * This function will provide the Ranking report for Amounts in USD settled incoming instructions everyday  
     * @param list iterator with Instructions     * 
     */
	StringBuilder generateRankingForIncoming(List<InstructionData> instructions);
	/**
     * This function will provide the Ranking report for Amounts in USD settled Outgoing instructions everyday  
     * @param list iterator with Instructions     * 
     */
	StringBuilder generateRankingForOutgoing(List<InstructionData> instructions);
	/**
     * This function will print the reports to console
     * @param list iterator with Instructions
     */
	StringBuilder generateInstructionReport(List<InstructionData> instructions);
	/**
     * This function will provide the trade amounts against each instruction  
     * @param list iterator with Instructions
     * @param the currency of the trade type(Sell or Buy)
     * Returns a Map with settlement date as key and trade amount as values
     */
	Map<LocalDate, BigDecimal> getSettlementDateWithTradeAmount(List<InstructionData> instructions, String tradeTypeFlag);
	 
	/**
     * This function will calculate Ranking of entities based on incoming and outgoing amount 
     * @param list iterator with Instructions
     * @param the currency of the trade type(Sell or Buy)
     * Returns a map with settlement date as key and Instructions data with Rank in this list as values
     */
	Map<LocalDate, List<InstructionData>> calculateRanking(List<InstructionData> instuctionsByTradeType);

	

}
