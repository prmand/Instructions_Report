package com.instruction.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import com.instruction.constants.InstructionConstants;

/**
 * provides a working day for trade currencies where the work week starts Sunday and ends Thursday for AED or SAR currencies.  
 *  If an instructed settlement date falls on a weekend, then the settlement date should be changed to the next working day
 * 
 * @author  Preetham M
 */

public class WorkingDaysServiceImpl implements WorkingDaysService {



	@Override
	public LocalDate getWorkingDay(LocalDate date, String currency) {
		DayOfWeek day = date.getDayOfWeek();
		if (checkWorkingDayForAEDOrSAR(day.name(), currency) || checkWorkingDayForOthers(day.name(), currency)) {
			return date;

		} else {
			return getWorkingDay(date.plusDays(1), currency);
		}

	}

	@Override
	public boolean checkWorkingDayForAEDOrSAR(String dayOfWeek, String currency) {
		if (currency.equalsIgnoreCase("AED") || currency.equalsIgnoreCase("SAR")) {
			for (InstructionConstants.workingDaysListForAEDOrSAR day : InstructionConstants.workingDaysListForAEDOrSAR.values()) {
				if (day.name().equals(dayOfWeek)) {
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public boolean checkWorkingDayForOthers(String dayOfWeek, String currency) {
		if (!(currency.equalsIgnoreCase("AED") || currency.equalsIgnoreCase("SAR"))) {
			for (InstructionConstants.workingDaysListForOthers day : InstructionConstants.workingDaysListForOthers.values()) {
				if (day.name().equals(dayOfWeek)) {
					return true;
				}
			}
		}
		return false;
	}
}
