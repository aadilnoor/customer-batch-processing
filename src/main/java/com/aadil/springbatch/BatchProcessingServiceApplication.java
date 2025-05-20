package com.aadil.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchProcessingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessingServiceApplication.class, args);
	}

}
