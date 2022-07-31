package com.correvate.javasampleproject;

import com.correvate.javasampleproject.config.StorageProperties;
import com.correvate.javasampleproject.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class JavaSampleProjectApplication {

	private final static Logger logger = LoggerFactory.getLogger(JavaSampleProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JavaSampleProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}


}
