package com.telecom.telecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.telecom.telecom.entities")
@EnableJpaRepositories("com.telecom.telecom.repositories")
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
