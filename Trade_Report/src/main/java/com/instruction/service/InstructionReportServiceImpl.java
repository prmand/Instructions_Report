package com.instruction.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import com.instruction.constants.InstructionConstants;
import com.instruction.model.InstructionData;

/**
 * 
 * Provides a report for the instructions to be settled with respect to
 * Instruction Date and the trade amount and provide a Report Ranking of
 * entities based on incoming and outgoing amount.
 * 
 * @author Preetham M
 */

public class InstructionReportServiceImpl implements InstructionReportService {

	@Override
	public StringBuilder generateReportForIncoming(List<InstructionData> instructions) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n*******************************\n");
		sb.append("     Daily Incoming Amount\n");
		sb.append("*********************************\n");
		sb.append("Date       ||       Amount\n");
		sb.append("*********************************\n");
		Map<LocalDate, BigDecimal> dailyIncomingAmounts = getSettlementDateWithTradeAmount(instructions,
				InstructionConstants.tradeTypeSellFlag);
		for (Entry<LocalDate, BigDecimal> entry : dailyIncomingAmounts.entrySet()) {
			sb.append("" + entry.getKey() + " ||      " + entry.getValue().setScale(2, BigDecimal.ROUND_HALF_EVEN)
					+ "\n");
		}

		return sb;
	}

	@Override
	public StringBuilder generateReportForOutgoing(List<InstructionData> instructions) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n*******************************\n");
		sb.append("     Daily Outgoing Amount\n");
		sb.append("*********************************\n");
		sb.append("Date       ||      Amount\n");
		sb.append("*********************************\n");
		Map<LocalDate, BigDecimal> dailyOutgoingAmounts = getSettlementDateWithTradeAmount(instructions,
				InstructionConstants.tradeTypeBuyFlag);
		for (Entry<LocalDate, BigDecimal> entry : dailyOutgoingAmounts.entrySet()) {
			sb.append("" + entry.getKey() + " ||      " + entry.getValue().setScale(2, BigDecimal.ROUND_HALF_EVEN)
					+ "\n");
		}
		return sb;
	}

	@Override
	public Map<LocalDate, BigDecimal> getSettlementDateWithTradeAmount(List<InstructionData> instructions, String tradeTypeFlag) {
		Map<LocalDate, BigDecimal> dailyAmounts = new HashMap<LocalDate, BigDecimal>();
		for (InstructionData instruction : instructions) {
			if (instruction.getBuyOrSellFlag().equals(tradeTypeFlag)) {
				WorkingDaysServiceImpl workingDays = new WorkingDaysServiceImpl();

				LocalDate date = workingDays.getWorkingDay(instruction.getSettlementDate(), instruction.getCurrency());
				if (instruction.getSettlementDate() != date) {
					instruction.setSettlementDate(date);
				}
				LocalDate settlementDate = instruction.getSettlementDate();
				if (dailyAmounts.keySet().contains(settlementDate)) {
					BigDecimal mapValue = dailyAmounts.get(settlementDate);
					mapValue = mapValue.add(instruction.getTradeAmount());
					dailyAmounts.put(instruction.getSettlementDate(), mapValue);
				} else {
					dailyAmounts.put(instruction.getSettlementDate(), instruction.getTradeAmount());
				}
			}
		}
		return dailyAmounts;
	}

	@Override
	public StringBuilder generateRankingForIncoming(List<InstructionData> instructions) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n**************************************\n");
		sb.append("     Daily Ranking for Incoming\n");
		sb.append("****************************************\n");
		sb.append("Date        || Rank || Entity ||  Amount \n");
		sb.append("****************************************\n");

		List<InstructionData> instuctionsSellType = new ArrayList<InstructionData>();
		for (InstructionData instruction : instructions) {
			if (instruction.getBuyOrSellFlag().equals(InstructionConstants.tradeTypeSellFlag)) {
				instuctionsSellType.add(instruction);
			}
		}
		Map<LocalDate, List<InstructionData>> instructionsByRank = calculateRanking(instuctionsSellType);

		for (Entry<LocalDate, List<InstructionData>> instructionByDate : instructionsByRank.entrySet()) {

			List<InstructionData> instructionsSortedByRank = instructionByDate.getValue();
			for (InstructionData instruction : instructionsSortedByRank) {
				sb.append("" + instruction.getSettlementDate() + "  ||  " + instruction.getRank() + "   ||    "
						+ instruction.getEntity() + "  ||  "
						+ instruction.getTradeAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n");
			}
		}

		return sb;
	}

	@Override
	public StringBuilder generateRankingForOutgoing(List<InstructionData> instructions) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n**************************************\n");
		sb.append("     Daily Ranking for Outgoing\n");
		sb.append("****************************************\n");
		sb.append("Date        || Rank || Entity ||  Amount \n");
		sb.append("****************************************\n");

		List<InstructionData> instuctionsBuyType = new ArrayList<InstructionData>();
		for (InstructionData instruction : instructions) {
			if (instruction.getBuyOrSellFlag().equals(InstructionConstants.tradeTypeBuyFlag)) {
				instuctionsBuyType.add(instruction);
			}
		}

		Map<LocalDate, List<InstructionData>> instructionsByRank = calculateRanking(instuctionsBuyType);

		for (Entry<LocalDate, List<InstructionData>> instructionByDate : instructionsByRank.entrySet()) {

			List<InstructionData> instructionsSortedByRank = instructionByDate.getValue();
			for (InstructionData instruction : instructionsSortedByRank) {
				sb.append("" + instruction.getSettlementDate() + "  ||  " + instruction.getRank() + "   ||    "
						+ instruction.getEntity() + "  ||  "
						+ instruction.getTradeAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n");
			}
		}

		return sb;
	}

	@Override
	public Map<LocalDate, List<InstructionData>> calculateRanking(List<InstructionData> instuctionsByTradeType) {

		Map<LocalDate, List<InstructionData>> instructionswithRankByDate = instuctionsByTradeType.stream()
				.collect(Collectors.groupingBy(InstructionData::getSettlementDate));

		for (Entry<LocalDate, List<InstructionData>> instructionByDate : instructionswithRankByDate.entrySet()) {
			AtomicInteger rank = new AtomicInteger(1);
			List<InstructionData> instructionsSortedByAmount = instructionByDate.getValue().stream()
					.sorted((a, b) -> a.getTradeAmount().compareTo(b.getTradeAmount())).collect(Collectors.toList());
			for (InstructionData instruction : instructionsSortedByAmount) {
				instruction.setRank(rank.getAndIncrement());
			}
		}

		return instructionswithRankByDate;

	}

	@Override
	public StringBuilder generateInstructionReport(List<InstructionData> instructions) {

		StringBuilder report = new StringBuilder();

		return report.append(generateReportForIncoming(instructions)).append(generateReportForOutgoing(instructions))
				.append(generateRankingForIncoming(instructions)).append(generateRankingForOutgoing(instructions));

	}

}
