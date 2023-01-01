package com.supermarket.api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
	@Autowired
	Environment environment;

	public String uploadImage(MultipartFile file, String fileName) {
		try {
			String UPLOAD_PATH = Path.of(Objects.requireNonNull(environment.getProperty("app.file.storage.mapping")))
					.toAbsolutePath().toString();

			Path destination = Paths.get(UPLOAD_PATH + "/" + fileName);

			Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

			return Constant.SERVER_PUBLIC_FOLDER_LINK + fileName;
		} catch (Exception e) {
			System.out.println("Error while uploading product. Please try again !!");
			return null;
		}
	}

	public static void deleteImage(String imgLink) {
		Runnable deleteTask = () -> {
			String UPLOAD_PATH = Paths.get(new ClassPathResource("/target/classes/public").getPath()).normalize()
					.toString();

			String[] linkIndfo = imgLink.split("/");

			String fileName = linkIndfo[linkIndfo.length - 1];

			File myObj = new File(UPLOAD_PATH + fileName);
			myObj.delete();
			return;
		};

		Thread deleteThread = new Thread(deleteTask);
		deleteThread.start();
	}
}
