package com.springbatch;

import java.io.File;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.UrlResource;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchApplication {

	public static void main(String[] args) {
		//System.setProperty("input", "file:"+new File("/SpringBatch/in.csv").getAbsolutePath());
		
		//System.setProperty("output", new File("E:\\Souvik\\STS-Project\\SpringBatch\\out.csv").getAbsolutePath());
		SpringApplication.run(SpringBatchApplication.class, args);
		
	}

}
