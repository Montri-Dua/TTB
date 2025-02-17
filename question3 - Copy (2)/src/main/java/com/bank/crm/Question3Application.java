package com.bank.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.bank.crm.entity")
@EnableJpaRepositories("com.bank.crm.repository")
public class Question3Application {

	public static void main(String[] args) {
		SpringApplication.run(Question3Application.class, args);
	}

}
