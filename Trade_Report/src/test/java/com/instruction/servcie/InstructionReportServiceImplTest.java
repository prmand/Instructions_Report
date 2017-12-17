package com.instruction.servcie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import org.junit.Test;
import com.instruction.constants.InstructionConstants;
import com.instruction.model.InstructionData;
import com.instruction.service.InstructionReportServiceImpl;

public class InstructionReportServiceImplTest {
	InstructionReportServiceImpl obj = new InstructionReportServiceImpl();

	@Test
	public void getSettlementDateWithTradeAmountTest() throws Exception {
		final LocalDate settleDate1 = LocalDate.of(2017, 12, 1);
		final LocalDate settleDate2 = LocalDate.of(2017, 12, 4);

		final List<InstructionData> testInstructions = new ArrayList<InstructionData>();
		testInstructions.add(new InstructionData("foo", "B", BigDecimal.valueOf(0.50), "SGP", LocalDate.of(2017, 12, 1),
				LocalDate.of(2017, 12, 1), 200, BigDecimal.valueOf(100.25)));
		testInstructions.add(new InstructionData("bar", "S", BigDecimal.valueOf(0.22), "AED", LocalDate.of(2017, 12, 3),
				LocalDate.of(2017, 12, 4), 450, BigDecimal.valueOf(150.50)));

		Map<LocalDate, BigDecimal> dailyAmounts = obj.getSettlementDateWithTradeAmount(testInstructions,
				InstructionConstants.tradeTypeBuyFlag);
		assertEquals(1, dailyAmounts.size());
		assertTrue(Objects.equals(dailyAmounts.get(settleDate1).setScale(2, BigDecimal.ROUND_HALF_EVEN),
				BigDecimal.valueOf(10025.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
		Map<LocalDate, BigDecimal> dailyAmounts1 = obj.getSettlementDateWithTradeAmount(testInstructions,
				InstructionConstants.tradeTypeSellFlag);
		assertEquals(1, dailyAmounts1.size());
		assertTrue(Objects.equals(dailyAmounts1.get(settleDate2).setScale(2, BigDecimal.ROUND_HALF_EVEN),
				BigDecimal.valueOf(14899.50).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
	}

	@Test
	public void calculateRankingTest_incoming() throws Exception {

		final LocalDate settleDate1 = LocalDate.of(2017, 12, 12);
		final LocalDate settleDate2 = LocalDate.of(2017, 12, 4);

		final List<InstructionData> testInstructions = new ArrayList<InstructionData>();

		testInstructions.add(new InstructionData("bar", "S", BigDecimal.valueOf(0.22), "AED", LocalDate.of(2017, 12, 3),
				LocalDate.of(2017, 12, 4), 450, BigDecimal.valueOf(150.50)));
		testInstructions.add(new InstructionData("soo", "S", BigDecimal.valueOf(0.97), "INR", LocalDate.of(2017, 12, 4),
				LocalDate.of(2017, 12, 4), 250, BigDecimal.valueOf(200.80)));
		testInstructions.add(new InstructionData("pqr", "S", BigDecimal.valueOf(0.94), "EUR",
				LocalDate.of(2017, 12, 10), LocalDate.of(2017, 12, 12), 111, BigDecimal.valueOf(160.60)));
		testInstructions.add(new InstructionData("jpa", "S", BigDecimal.valueOf(0.61), "GBP",
				LocalDate.of(2017, 12, 10), LocalDate.of(2017, 12, 12), 221, BigDecimal.valueOf(221.44)));

		Map<LocalDate, List<InstructionData>> instructionsByRank = obj.calculateRanking(testInstructions);

		assertEquals(instructionsByRank.get(settleDate1).size(), 2);
		assertEquals(instructionsByRank.get(settleDate2).size(), 2);

		assertEquals((instructionsByRank.get(settleDate1)).get(0).getRank(), 1);
		assertEquals((instructionsByRank.get(settleDate1)).get(1).getRank(), 2);

		assertEquals((instructionsByRank.get(settleDate2)).get(0).getRank(), 1);
		assertEquals((instructionsByRank.get(settleDate2)).get(1).getRank(), 2);

	}

	@Test
	public void calculateRankingTest_outgoing() throws Exception {

		final LocalDate settleDate1 = LocalDate.of(2017, 12, 12);
		final LocalDate settleDate2 = LocalDate.of(2017, 12, 6);
		final LocalDate settleDate3 = LocalDate.of(2017, 12, 1);

		final List<InstructionData> testInstructions = new ArrayList<InstructionData>();

		testInstructions.add(new InstructionData("foo", "B", BigDecimal.valueOf(0.50), "SGP", LocalDate.of(2017, 12, 1),
				LocalDate.of(2017, 12, 1), 200, BigDecimal.valueOf(100.25)));
		testInstructions.add(new InstructionData("mal", "B", BigDecimal.valueOf(0.36), "SAR", LocalDate.of(2017, 12, 1),
				LocalDate.of(2017, 12, 1), 110, BigDecimal.valueOf(122.41)));
		testInstructions.add(new InstructionData("sql", "B", BigDecimal.valueOf(0.54), "GBP", LocalDate.of(2017, 12, 1),
				LocalDate.of(2017, 12, 1), 220, BigDecimal.valueOf(121.11)));
		testInstructions.add(new InstructionData("prf", "B", BigDecimal.valueOf(0.53), "EUR", LocalDate.of(2017, 12, 5),
				LocalDate.of(2017, 12, 6), 110, BigDecimal.valueOf(210.60)));
		testInstructions.add(new InstructionData("jaa", "B", BigDecimal.valueOf(0.61), "EUR",
				LocalDate.of(2017, 12, 10), LocalDate.of(2017, 12, 12), 110, BigDecimal.valueOf(201.44)));

		Map<LocalDate, List<InstructionData>> instructionsByRank = obj.calculateRanking(testInstructions);

		assertEquals(instructionsByRank.get(settleDate1).size(), 1);
		assertEquals(instructionsByRank.get(settleDate2).size(), 1);
		assertEquals(instructionsByRank.get(settleDate3).size(), 3);

		assertEquals((instructionsByRank.get(settleDate1)).get(0).getRank(), 1);
		assertEquals((instructionsByRank.get(settleDate2)).get(0).getRank(), 1);

	}

}
