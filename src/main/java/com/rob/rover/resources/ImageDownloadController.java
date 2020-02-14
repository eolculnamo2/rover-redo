package com.rob.rover.resources;

import com.rob.rover.client.RoverClient;
import com.rob.rover.enums.RoverEnum;
import com.rob.rover.service.DownloadPhotoService;
import com.rob.rover.shared.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/download")
public class ImageDownloadController {
	private final RoverClient roverClient;
	private final DownloadPhotoService downloadPhotoService;

	private static final Logger logger = LoggerFactory.getLogger(ImageDownloadController.class);
	private final String DATE_FORMAT = "yyyy-MM-dd";

	public ImageDownloadController(final RoverClient roverClient, final DownloadPhotoService downloadPhotoService) {
		this.roverClient = roverClient;
		this.downloadPhotoService = downloadPhotoService;
	}

	@PostMapping("/{date}")
	public Response downloadImagesForDate(@PathVariable("date") String date) {
		String formattedDate = Helpers.formatDate(date, DATE_FORMAT);

		if (formattedDate == null) {
			logger.warn(date + " is an invalid date.");
			return Response.status(Status.NOT_ACCEPTABLE).entity(Collections.EMPTY_LIST).build();
		}

		List<String> roverUrls = roverClient.getPhotosByDate(formattedDate, RoverEnum.CURIOSITY, "all");
		downloadPhotoService.downloadPhotos(roverUrls, formattedDate);
		return Response.status(Status.OK).entity(roverUrls).build();
	}
}
