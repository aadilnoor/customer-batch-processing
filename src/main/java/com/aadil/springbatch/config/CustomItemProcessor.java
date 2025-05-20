package com.aadil.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.aadil.springbatch.entity.Customer;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer> {

	private static final Logger logger = LoggerFactory.getLogger(CustomItemProcessor.class);

	@Override
	public Customer process(Customer customer) throws Exception {
		if (customer.getEmail() == null || !customer.getEmail().contains("@")) {
			logger.warn("Skipping customer (Invalid Email): {}", customer.getEmail());
			return null;
		}

		if (customer.getContactNo() == null || customer.getContactNo().length() < 10) {
			logger.warn("Skipping customer (Invalid Contact): {}", customer.getContactNo());
			return null;
		}
		return customer;
	}
}