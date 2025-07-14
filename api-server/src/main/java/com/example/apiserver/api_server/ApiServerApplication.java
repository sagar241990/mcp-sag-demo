package com.example.apiserver.api_server;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiServerApplication {

	//logger
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ApiServerApplication.class);

	public static void main(String[] args) {

		logger.info("Starting ApiServerApplication...");
		SpringApplication.run(ApiServerApplication.class, args);
	}

}
