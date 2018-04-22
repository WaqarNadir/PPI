package com.erp.classes;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Functions {
	public static DateFormat SlashDateformatter = new SimpleDateFormat("dd/MM/YYYY");
	public static DateFormat DashDateformatter = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		return date;

	}

	public static String DateFormatterDash(Date date) {
		return DashDateformatter.format(date);
	}

	public static String DateFormatterSlash(Date date) {
		return SlashDateformatter.format(date);
	}

	public static Date addDays(int days, Date date) {
		Date currentDate = new Date(System.currentTimeMillis());
		if (date != null) {
			currentDate = date;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, days);
		Long timeInMilli = cal.getTimeInMillis();
		Date endDate = new Date(timeInMilli);
		return endDate;
	}

	public static Date AddMonth(int months, Date date) {
		Date currentDate = new Date(System.currentTimeMillis());
		if (date != null) {
			currentDate = date;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(Calendar.MONTH, months);
		Long timeInMilli = cal.getTimeInMillis();
		Date endDate = new Date(timeInMilli);
		return endDate;
	}

	public static Date thisMonth(Date date) {
		Date startOfMonth = new Date(System.currentTimeMillis());
		if (date != null) {
			startOfMonth = new Date(date.getTime());
		}
		startOfMonth.setDate(1);
		return startOfMonth;
	}

	public static Date thisYear(Date date) {
		Date startOfMonth = new Date(System.currentTimeMillis());
		if (date != null) {
			startOfMonth = new Date(date.getTime());
		}
		startOfMonth.setDate(1);
		startOfMonth.setMonth(1);
		return startOfMonth;
	}

	public static Date getSQLDate(String dateString) {
		Date sqlDate = null;
		try {
			java.util.Date date = formatDate(dateString);
			sqlDate = new Date(date.getTime());
		} catch (Exception e) {
			System.err.println("Error while formating date" + dateString);
		}

		return sqlDate;

	}

	public static java.util.Date formatDate(String dateString) {

		try {
			java.util.Date date = DashDateformatter.parse(dateString);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTwoDigitInt(int number) {
		return String.format("%02d", number);
	}

}
