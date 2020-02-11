package com.rob.rover.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.rob.rover.constants.ClientConstants;
import com.rob.rover.dto.PhotoDto;
import com.rob.rover.dto.PhotoResponseDto;
import com.rob.rover.enums.RoverEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoverClient {
	private ObjectReader objectReader;

	public RoverClient() {
		objectReader = new ObjectMapper().readerFor(PhotoResponseDto.class);
	}

	public List<String> getPhotosByDate(String sol, RoverEnum rover, String camera) {
		WebClient client = WebClient.create(ClientConstants.API_URL);
		String response = client.get()
			.uri(uriBuilder -> uriBuilder
			.path("/{rover}/photos")
			.queryParam("sol", sol)
			.queryParam("camera", camera != "all" ? camera : null)
			.queryParam("api_key", ClientConstants.API_KEY)
			.build(rover))
		.retrieve()
		.bodyToMono(String.class)
		.block();

		try {
			PhotoResponseDto photos = objectReader.readValue(response);
			return photos.getPhotos().stream().map(x -> x.getImgSrc()).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
