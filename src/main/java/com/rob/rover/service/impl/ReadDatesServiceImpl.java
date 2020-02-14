package com.rob.rover.service.impl;

import com.rob.rover.service.ReadDatesService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadDatesServiceImpl implements ReadDatesService {
	private final String DATES_FILE = "src/main/resources/static/dates.txt";

	public List<String> readDatesFromFile() {
		List<String> allDates = new ArrayList<>(4);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(DATES_FILE));

			String line;
			while ((line = reader.readLine()) != null) {
				allDates.add(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return allDates;
	}
}
