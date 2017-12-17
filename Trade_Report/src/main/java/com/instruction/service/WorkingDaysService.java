package com.instruction.service;

import java.time.LocalDate;

public interface WorkingDaysService {
	/**
     * This function will check whether an instructed settlement date falls on a weekend for the currency of the trade is AED or SAR 
     * @param instruction settlement day
     * @param the currency of the trade
     * Returns <tt>true</tt> if an instructed settlement date between Sunday and Thursday
     */
	boolean checkWorkingDayForAEDOrSAR(String dayOfWeek, String currency);
	/**
     * This function will check whether an instructed settlement date falls on a weekend for other currencies 
     * @param instruction settlement day
     * @param the currency of the trade
     * Returns <tt>true</tt> if an instructed settlement date between Monday and Friday
     */
	boolean checkWorkingDayForOthers(String dayOfWeek, String currency);
	/**
     * This function to calculate settlement date for given instruction
     * @param instruction settlement date
     * @param the currency of the trade
     * Returns settlement date If an instructed settlement date falls on a weekend, then the settlement date should be changed to the next working day. 
     */
	LocalDate getWorkingDay(LocalDate date, String currency);
}
