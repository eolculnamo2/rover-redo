package com.rob.rover.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.rob.rover.client.RoverClient;
import com.rob.rover.constants.ClientConstants;
import com.rob.rover.constants.DownloadConstants;
import com.rob.rover.dto.PhotoDto;
import com.rob.rover.dto.PhotoResponseDto;
import com.rob.rover.resources.ImageDownloadController;
import com.rob.rover.service.DownloadPhotoService;
import com.rob.rover.service.ReadDatesService;
import com.rob.rover.service.impl.DownloadPhotoServiceImpl;
import com.rob.rover.service.impl.ReadDatesServiceImpl;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import reactor.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

@TestInstance(Lifecycle.PER_CLASS)
public class DownloadPhotoServiceTest {
	private ImageDownloadController imageDownloadController;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private WireMockServer wireMockServer;

	@BeforeAll
	public void setup() {
		RoverClient roverClient = new RoverClient();
		DownloadPhotoService downloadPhotoService = new DownloadPhotoServiceImpl();
		ReadDatesService readDatesService = new ReadDatesServiceImpl();

		imageDownloadController = new ImageDownloadController(roverClient, downloadPhotoService, readDatesService);

		wireMockServer = new WireMockServer(9272, 9277);
		wireMockServer.start();
	}

	@BeforeEach
	public void beforeTest() throws IOException {
		FileUtils.cleanDirectory(new File(DownloadConstants.SAVE_PATH));
	}

	@AfterAll
	public void tearDown() throws IOException {
		wireMockServer.stop();
		FileUtils.cleanDirectory(new File(DownloadConstants.SAVE_PATH));
	}

	@Test
	public void testDownloadPhotos() throws JsonProcessingException {
		List<PhotoDto> photos = new ArrayList<>(1);

		PhotoDto photo = new PhotoDto();
		photo.setImgSrc("https://avatars1.githubusercontent.com/u/27943776?s=460&v=4");
		photos.add(photo);

		PhotoResponseDto photoResponseDto = new PhotoResponseDto();
		photoResponseDto.setPhotos(photos);
		configureFor(9272);
		stubFor(any(urlMatching(ClientConstants.API_URL)).willReturn(aResponse().withBody(objectMapper.writeValueAsString(photoResponseDto))));

		imageDownloadController.downloadDatesFromDatesFile();

		File dateDir1 = new File(DownloadConstants.SAVE_PATH + "/2016-07-13");
		File dateDir2 = new File(DownloadConstants.SAVE_PATH + "/2017-02-27");
		File dateDir3 = new File(DownloadConstants.SAVE_PATH + "/2018-05-01");
		File dateDir4 = new File(DownloadConstants.SAVE_PATH + "/2018-06-02");

		Assert.isTrue(dateDir1.exists());
		Assert.isTrue(dateDir2.exists());
		Assert.isTrue(dateDir3.exists());
		Assert.isTrue(dateDir4.exists());
	}

}
