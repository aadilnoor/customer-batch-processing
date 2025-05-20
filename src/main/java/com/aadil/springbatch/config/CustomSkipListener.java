package com.aadil.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import com.aadil.springbatch.entity.Customer;

@Component
public class CustomSkipListener implements SkipListener<Customer, Customer> {

	private static final Logger logger = LoggerFactory.getLogger(CustomSkipListener.class);

	@Override
	public void onSkipInRead(Throwable t) {
		logger.warn("Skipped record while reading due to: {}", t.getMessage());
	}

	@Override
	public void onSkipInProcess(Customer customer, Throwable t) {
		logger.warn("Skipped customer {} due to: {}", customer.getEmail(), t.getMessage());
	}

	@Override
	public void onSkipInWrite(Customer customer, Throwable t) {
		logger.warn("Failed to write customer {} due to: {}", customer.getEmail(), t.getMessage());
	}
}