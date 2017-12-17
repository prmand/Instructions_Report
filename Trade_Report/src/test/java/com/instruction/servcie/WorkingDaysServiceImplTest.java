package com.instruction.servcie;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Test;
import com.instruction.model.InstructionData;
import com.instruction.service.WorkingDaysServiceImpl;

public class WorkingDaysServiceImplTest {
	WorkingDaysServiceImpl obj = new WorkingDaysServiceImpl();

	@Test
	public void getWorkingDay_settlement_samedayTest() throws Exception {

		final LocalDate initialSettlementDate = LocalDate.of(2017, 12, 4);

		final InstructionData testInstruction = new InstructionData("bar", "S", BigDecimal.valueOf(0.22), "AED",
				LocalDate.of(2017, 12, 3), LocalDate.of(2017, 12, 4), 450, BigDecimal.valueOf(150.50));

		LocalDate settlementDate = obj.getWorkingDay(testInstruction.getSettlementDate(),
				testInstruction.getCurrency());

		assertEquals(settlementDate, initialSettlementDate);
	}

	@Test
	public void getWorkingDay_settlementAER_differentdayTest() throws Exception {

		final LocalDate initialSettlementDate = LocalDate.of(2017, 12, 4);

		final InstructionData testInstruction = new InstructionData("bar", "S", BigDecimal.valueOf(0.22), "AED",
				LocalDate.of(2017, 12, 3), LocalDate.of(2017, 12, 4), 450, BigDecimal.valueOf(150.50));

		LocalDate settlementDate = obj.getWorkingDay(testInstruction.getSettlementDate(),
				testInstruction.getCurrency());

		assertEquals(settlementDate, initialSettlementDate);
	}

	@Test
	public void getWorkingDay_settlementSAR_differentdayTest() throws Exception {

		final LocalDate initialSettlementDate = LocalDate.of(2017, 12, 10);

		final InstructionData testInstruction = new InstructionData("mal", "B", BigDecimal.valueOf(0.36), "SAR",
				LocalDate.of(2017, 12, 8), LocalDate.of(2017, 12, 8), 110, BigDecimal.valueOf(122.41));

		LocalDate settlementDate = obj.getWorkingDay(testInstruction.getSettlementDate(),
				testInstruction.getCurrency());

		assertEquals(settlementDate, initialSettlementDate);
	}

	@Test
	public void checkWorkingDayForAEDOrSARTest() throws Exception {

		final InstructionData testInstruction = new InstructionData("mal", "B", BigDecimal.valueOf(0.36), "SAR",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 4), 110, BigDecimal.valueOf(122.41));
		final InstructionData testInstruction1 = new InstructionData("soo", "B", BigDecimal.valueOf(0.36), "AED",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 4), 110, BigDecimal.valueOf(122.41));
		final InstructionData testInstruction2 = new InstructionData("nam", "B", BigDecimal.valueOf(0.36), "SAR",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 8), 110, BigDecimal.valueOf(122.41));
		final InstructionData testInstruction3 = new InstructionData("ram", "B", BigDecimal.valueOf(0.36), "AED",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 8), 110, BigDecimal.valueOf(122.41));
		assertTrue(obj.checkWorkingDayForAEDOrSAR(testInstruction.getSettlementDate().getDayOfWeek().name(),
				testInstruction.getCurrency()));
		assertTrue(obj.checkWorkingDayForAEDOrSAR(testInstruction1.getSettlementDate().getDayOfWeek().name(),
				testInstruction1.getCurrency()));
		assertFalse(obj.checkWorkingDayForAEDOrSAR(testInstruction2.getSettlementDate().getDayOfWeek().name(),
				testInstruction2.getCurrency()));
		assertFalse(obj.checkWorkingDayForAEDOrSAR(testInstruction3.getSettlementDate().getDayOfWeek().name(),
				testInstruction3.getCurrency()));
	}

	@Test
	public void checkWorkingDayForOthersTest() throws Exception {

		final InstructionData testInstruction = new InstructionData("bar", "B", BigDecimal.valueOf(0.36), "GBP",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 4), 110, BigDecimal.valueOf(122.41));
		final InstructionData testInstruction1 = new InstructionData("rqm", "B", BigDecimal.valueOf(0.36), "EUR",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 4), 110, BigDecimal.valueOf(122.41));
		final InstructionData testInstruction2 = new InstructionData("ame", "B", BigDecimal.valueOf(0.36), "INR",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 9), 110, BigDecimal.valueOf(122.41));
		final InstructionData testInstruction3 = new InstructionData("nee", "B", BigDecimal.valueOf(0.36), "SGP",
				LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 9), 110, BigDecimal.valueOf(122.41));
		assertTrue(obj.checkWorkingDayForOthers(testInstruction.getSettlementDate().getDayOfWeek().name(),
				testInstruction.getCurrency()));
		assertTrue(obj.checkWorkingDayForOthers(testInstruction1.getSettlementDate().getDayOfWeek().name(),
				testInstruction1.getCurrency()));
		assertFalse(obj.checkWorkingDayForOthers(testInstruction2.getSettlementDate().getDayOfWeek().name(),
				testInstruction2.getCurrency()));
		assertFalse(obj.checkWorkingDayForOthers(testInstruction3.getSettlementDate().getDayOfWeek().name(),
				testInstruction3.getCurrency()));
	}
}
