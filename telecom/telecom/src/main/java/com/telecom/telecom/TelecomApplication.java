package com.telecom.telecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TelecomApplication {

	public static void main(String[] args) {
		// Start the Spring Boot application
		ConfigurableApplicationContext context = SpringApplication.run(TelecomApplication.class, args);

		// Retrieve the environment and get the port property
		String port = context.getEnvironment().getProperty("server.port", "8082"); // Default to 8082 if not set
		System.out.println("----------------------------------------" + "\n"
						  + "Application is running on port: " + port + "\n"
				          + "----------------------------------------");
	}
}
