package com.martin.indexy.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dater {

	private static DateFormat dateAndTime = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	private static DateFormat dateAndTimeFilename = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

	public static String getDateAndTimeString() {
		return dateAndTime.format(new Date()) + ": ";
	}

	public static String getDateAndTimeFilename() {
		return dateAndTimeFilename.format(new Date());
	}
}
