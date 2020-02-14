package com.rob.rover.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
	public static String formatDate(String dateString, String format) {
		if (dateString == null) {
			return null;
		}

		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		dateFormatter.setLenient(false);

		try {
			Date parsedDate = dateFormatter.parse(dateString);
			return dateFormatter.format(parsedDate);
		} catch(IllegalArgumentException | ParseException e) {
			return null;
		}
	}
}
