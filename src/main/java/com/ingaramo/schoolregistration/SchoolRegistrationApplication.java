package com.ingaramo.schoolregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ingaramo.schoolregistration")
public class SchoolRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolRegistrationApplication.class, args);
	}

}
