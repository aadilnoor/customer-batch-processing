package com.aadil.springbatch.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aadil.springbatch.entity.UploadedFile;
import com.aadil.springbatch.repository.UploadedFileRepository;
import com.aadil.springbatch.util.FileUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final JobLauncher jobLauncher;
	private final Job job;

	private final UploadedFileRepository uploadedFileRepository;

	@GetMapping("/import")
	public String importCsvToDB() {
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()) // unique
																													// instance
					.toJobParameters();

			jobLauncher.run(job, jobParameters);
			return "CSV Import Started Successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to start import job: " + e.getMessage();
		}
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Upload CSV and trigger batch job")
	public ResponseEntity<String> uploadAndRunJob(
			@Parameter(description = "CSV file to upload", required = true) @RequestParam("file") MultipartFile file) {
		try {
			// Create uploads dir if not exists
			File uploadDir = new File("uploads");
			if (!uploadDir.exists())
				uploadDir.mkdirs();

			// Step 1: Calculate hash
			String hash = FileUtils.calculateFileHash(file);

			// Step 2: Check if already uploaded
			if (uploadedFileRepository.existsByFileHash(hash)) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("This file has already been uploaded and processed.");
			}

			// Step 3: Save file
			String filename = file.getOriginalFilename();
			Path filepath = Paths.get("uploads", filename);
			Files.write(filepath, file.getBytes());

			// Step 4: Start job
			JobParameters jobParameters = new JobParametersBuilder().addString("fileName", filename)
					.addLong("startAt", System.currentTimeMillis()).toJobParameters();

			jobLauncher.run(job, jobParameters);

			// Step 5: Save hash record
			UploadedFile uploaded = new UploadedFile();
			uploaded.setFileName(filename);
			uploaded.setFileHash(hash);
			uploaded.setUploadedAt(LocalDateTime.now());
			uploadedFileRepository.save(uploaded);

			return ResponseEntity.ok("File uploaded and batch job started!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

}
