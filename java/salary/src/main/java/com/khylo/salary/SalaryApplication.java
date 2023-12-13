package com.khylo.salary;

import com.khylo.salary.config.VaultConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(VaultConfig.class)
public class SalaryApplication implements CommandLineRunner {

	private final VaultConfig configuration;

	public SalaryApplication(VaultConfig configuration) {
		this.configuration = configuration;
	}


	public static void main(String[] args) {
		SpringApplication.run(SalaryApplication.class, args);
	}

	@Value("${db.password}")
	String dbPassword;
	@Value("${example.password}")
	String examplePassword;

	@Override
	public void run(String... args) {

		Logger logger = LoggerFactory.getLogger(SalaryApplication.class);

		logger.info("----------------------------------------");
		logger.info("Configuration properties");
		logger.info("   example.username is {}", configuration.getUsername());
		logger.info("   example.password is {}", configuration.getPassword());
		logger.info("   example is {}",examplePassword);
		logger.info("   dbPassword is {}", dbPassword);
		logger.info("----------------------------------------");
	}

}