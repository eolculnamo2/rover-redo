package com.rob.rover.resources;

import com.rob.rover.client.RoverClient;
import com.rob.rover.enums.RoverEnum;
import com.rob.rover.service.DownloadPhotoService;
import com.rob.rover.service.ReadDatesService;
import com.rob.rover.shared.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/download")
public class ImageDownloadController {
	private final RoverClient roverClient;
	private final DownloadPhotoService downloadPhotoService;
	private final ReadDatesService readDatesService;

	private static final Logger logger = LoggerFactory.getLogger(ImageDownloadController.class);

	public ImageDownloadController(final RoverClient roverClient, final DownloadPhotoService downloadPhotoService, final ReadDatesService readDatesService) {
		this.roverClient = roverClient;
		this.downloadPhotoService = downloadPhotoService;
		this.readDatesService = readDatesService;
	}

	@PostMapping("/{date}")
	public Response downloadImagesForDate(@PathVariable("date") String date) {
		List<String> roverUrls = downloadPhotos(date);

		if (roverUrls == null) {
			Response.status(Status.NOT_ACCEPTABLE).entity(Collections.EMPTY_LIST).build();
		}

		return Response.status(Status.OK).entity(roverUrls).build();
	}

	@PostMapping
	public Response downloadDatesFromDatesFile() {
		List<String> allDates = readDatesService.readDatesFromFile();
		List<String> roverUrls = new ArrayList<>();

		for (String date : allDates) {
			List<String> urls = downloadPhotos(date);
			if (urls != null) {
				roverUrls.addAll(urls);
				continue;
			}
			logger.warn(new StringBuilder("Unable to retrieve urls for date ").append(date).toString());
		}

		return Response.status(Status.OK).entity(roverUrls).build();
	}

	private List<String> downloadPhotos(String date) {
		String formattedDate = Helpers.formatDate(date);

		if (formattedDate == null) {
			logger.warn(date + " is an invalid date.");
			return null;
		}

		List<String> roverUrls = roverClient.getPhotosByDate(formattedDate, RoverEnum.CURIOSITY, "all");
		downloadPhotoService.downloadPhotos(roverUrls, formattedDate);
		return roverUrls;
	}
}
