package com.aadil.springbatch.util;

import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;

public class FileUtils {

	public static String calculateFileHash(MultipartFile file) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(file.getBytes());

		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			hexString.append(String.format("%02x", b));
		}

		return hexString.toString();
	}
}
