package com.aadil.springbatch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job job;

	/*
	 * @Scheduled(initialDelay = 10000, fixedRate = 60000) // Start after 10 sec,
	 * then run every 60 sec public void runBatchJob() { try { JobParameters
	 * jobParameters = new JobParametersBuilder() .addLong("time",
	 * System.currentTimeMillis()) // Unique param .toJobParameters();
	 * 
	 * jobLauncher.run(job, jobParameters);
	 * System.out.println("Batch job triggered by scheduler");
	 * 
	 * } catch (Exception e1  b) { System.out.println("Job failed: " + e.getMessage());
	 * e.printStackTrace(); } }
	 */
}
