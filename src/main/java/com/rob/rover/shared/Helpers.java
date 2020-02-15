package com.rob.rover.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Helpers {
	public static String formatDate(String dateString) {
		final List<String> formats = Arrays.asList("yyyy-MM-dd", "MMM d, yyyy", "MMM-d-yyyy", "MM/dd/yy");
		if (dateString == null) {
			return null;
		}

		for (String format : formats) {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
				Date parsedDate = dateFormatter.parse(dateString);
				if (parsedDate != null) {
					return new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
				}
			} catch(IllegalArgumentException | ParseException e){
				// no op.. continue
			}
		}
		return null;
	}
}
