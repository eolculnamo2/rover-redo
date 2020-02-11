package com.rob.rover.service.impl;

import com.rob.rover.service.DownloadPhotoService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class DownloadPhotoServiceImpl implements DownloadPhotoService {
	private final String SAVE_PATH = "src/main/resources/static/saved-photos/";

	public void downloadPhotos(List<String> photoUrls) {
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for(String url : photoUrls) {
		  CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
			try {
				BufferedImage img = ImageIO.read(new URL(url));
				String urlForFile = new StringBuilder(SAVE_PATH)
					.append(UUID.randomUUID().toString())
					.append(".png")
					.toString();
				File imgFile = new File(urlForFile);
				System.out.println("Saving " + urlForFile);
				ImageIO.write(img, "png", imgFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		  });
		  futures.add(completableFuture);
		}
		// Wait for all to complete
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
	}
}
