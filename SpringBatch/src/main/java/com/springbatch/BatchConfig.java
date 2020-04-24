package com.springbatch;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;



@Configuration
public class BatchConfig {

	@Bean
	public Job job(JobBuilderFactory jbf, StepBuilderFactory sbf, 
			ItemReader<User> itemReader, ItemWriter<User> itemWriter) {
		
		Step step = sbf.get("Step-1")
				.<User,User>chunk(1)
				.reader(itemReader)
				.writer(itemWriter)
				.build();
		
		return jbf.get("ETL-Job")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<User> fileItemReader(@Value("${input}") Resource resource) throws Exception{
		
		return new FlatFileItemReaderBuilder<User>()
				.resource(resource)
				.name("CSV-Reader")
				.targetType(User.class)
				.delimited().delimiter(",").names(new String[] {"id","name","email"})
				.build();
	}
	
	
	@Bean
	public JdbcBatchItemWriter<User> batchItemWriter(DataSource dataSource){
		
		return new JdbcBatchItemWriterBuilder<User>()
				.dataSource(dataSource)
				.sql("insert into User(id,name,email) values (:id, :name, :email)")
				.beanMapped() //beanMapper() is used to map the parameter (id/name/email) from ItemReader to above SQL
				.build();
	}
	
	
	
	
}
