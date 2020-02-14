package com.rob.rover.service;

import java.util.List;

public interface DownloadPhotoService {
	void downloadPhotos(List<String> photoUrls, String folderName);
}
