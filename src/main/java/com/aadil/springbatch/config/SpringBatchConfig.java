package com.aadil.springbatch.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.aadil.springbatch.entity.Customer;
import com.aadil.springbatch.repository.CustomerRepository;

@Configuration
public class SpringBatchConfig {

	private final Logger logger = LoggerFactory.getLogger(ItemWriter.class);

	@Bean
	public Job jobBean(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
		return new JobBuilder("job", jobRepository).listener(listener).start(step1).build();
	}

	@Bean
	public Step step(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
			FlatFileItemReader<Customer> fileItemReader, ItemProcessor<Customer, Customer> itemProcessor,
			ItemWriter<Customer> itemWriter, CustomSkipListener skipListener) {

		return new StepBuilder("jobStep", jobRepository).<Customer, Customer>chunk(250, transactionManager)
				.reader(fileItemReader).processor(itemProcessor).writer(itemWriter).faultTolerant()
				.skip(Throwable.class).skipLimit(100).listener(skipListener).build();
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// file reader
	/*
	 * @Bean public FlatFileItemReader<Customer> fileItemReader() {
	 * 
	 * return new FlatFileItemReaderBuilder<Customer>().name("itemReader")
	 * .resource(new ClassPathResource("customers.csv")).delimited() .names("id",
	 * "firstName", "lastName", "email", "gender", "contactNo", "country", "dob")
	 * .targetType(Customer.class).build(); }
	 */

	@Bean
	@StepScope
	public FlatFileItemReader<Customer> reader(@Value("#{jobParameters['fileName']}") String fileName) {
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("uploads/" + fileName));
		reader.setLinesToSkip(1); // Skip header

		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

		BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Customer.class);

		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		reader.setLineMapper(lineMapper);
		return reader;
	}

	// file processor
	@Bean
	public ItemProcessor<Customer, Customer> itemProcessor() {
		return new CustomItemProcessor();
	}

	// file writer
	@Bean
	public ItemWriter<Customer> itemWriter(CustomerRepository customerRepository) {
		return new ItemWriter<Customer>() {

			@Override
			public void write(Chunk<? extends Customer> chunk) throws Exception {
				for (Customer c : chunk.getItems()) {
					logger.info("Trying to save customer: {}", c);
				}
				customerRepository.saveAll(chunk.getItems());
				logger.info("Saved {} customers to DB.", chunk.size());
			}

		};
	}

}
