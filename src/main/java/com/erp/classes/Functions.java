package com.erp.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;

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

}
