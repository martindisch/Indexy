package com.martin.indexy.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dater {

	private static DateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	public static String dateString() {
		return getDate() + ": ";
	}

	private static String getDate() {
		return dateFormat.format(new Date());
	}

}
