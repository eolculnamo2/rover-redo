package com.rob.rover.resources;

import com.rob.rover.client.RoverClient;
import com.rob.rover.enums.RoverEnum;
import com.rob.rover.service.DownloadPhotoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/download")
public class ImageDownloadController {
	private final RoverClient roverClient;
	private final DownloadPhotoService downloadPhotoService;

	public ImageDownloadController(final RoverClient roverClient, final DownloadPhotoService downloadPhotoService) {
		this.roverClient = roverClient;
		this.downloadPhotoService = downloadPhotoService;
	}

	@PostMapping("/{date}")
	public List<String> downloadImagesForDate(@PathVariable("date") String date) {
		// todo validate date.

		List<String> roverUrls = roverClient.getPhotosByDate(date, RoverEnum.CURIOSITY, "all");
		downloadPhotoService.downloadPhotos(roverUrls);
		return roverUrls;
	}
}
