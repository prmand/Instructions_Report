package com.instruction.Trade_Report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.instruction.model.InstructionData;
import com.instruction.service.InstructionReportServiceImpl;

/**
 * Main class
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	InstructionReportServiceImpl obj = new InstructionReportServiceImpl();
		List<InstructionData> instructions = new ArrayList<InstructionData>();
		instructions.add(new InstructionData("foo","B",BigDecimal.valueOf(0.50),"SGP",LocalDate.of(2017, 12, 1),LocalDate.of(2017, 12, 1),200,BigDecimal.valueOf(100.25)));
		instructions.add(new InstructionData("mal","B",BigDecimal.valueOf(0.36),"SAR",LocalDate.of(2017, 12, 1),LocalDate.of(2017, 12, 1),110,BigDecimal.valueOf(122.41)));	
		instructions.add(new InstructionData("sql","B",BigDecimal.valueOf(0.54),"GBP",LocalDate.of(2017, 12, 1),LocalDate.of(2017, 12, 1),220,BigDecimal.valueOf(121.11)));
		instructions.add(new InstructionData("prf","B",BigDecimal.valueOf(0.53),"EUR",LocalDate.of(2017, 12, 5),LocalDate.of(2017, 12, 6),110,BigDecimal.valueOf(210.60)));
		instructions.add(new InstructionData("jaa","B",BigDecimal.valueOf(0.61),"EUR",LocalDate.of(2017, 12, 10),LocalDate.of(2017, 12, 12),110,BigDecimal.valueOf(201.44)));
		instructions.add(new InstructionData("bar","S",BigDecimal.valueOf(0.22),"AED",LocalDate.of(2017, 12, 3),LocalDate.of(2017, 12, 4),450,BigDecimal.valueOf(150.50)));
		instructions.add(new InstructionData("soo","S",BigDecimal.valueOf(0.97),"INR",LocalDate.of(2017, 12, 4),LocalDate.of(2017, 12, 4),250,BigDecimal.valueOf(200.80)));
		instructions.add(new InstructionData("pqr","S",BigDecimal.valueOf(0.94),"EUR",LocalDate.of(2017, 12, 10),LocalDate.of(2017, 12, 12),111,BigDecimal.valueOf(160.60)));
		instructions.add(new InstructionData("jpa","S",BigDecimal.valueOf(0.61),"GBP",LocalDate.of(2017, 12, 10),LocalDate.of(2017, 12, 12),221,BigDecimal.valueOf(221.44)));
		System.out.println(obj.generateInstructionReport(instructions));
     
    }
}
