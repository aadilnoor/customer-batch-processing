package com.aadil.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

	private Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("Job '{}' is starting at {}", jobExecution.getJobInstance().getJobName(),
				jobExecution.getStartTime());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		BatchStatus status = jobExecution.getStatus();

		switch (status) {
		case COMPLETED -> logger.info("Job completed successfully.");
		case FAILED -> logger.error("Job failed!");
		case STOPPED -> logger.warn("Job was stopped manually.");
		default -> logger.warn("Job finished with status: {}", status);
		}
	}

}
