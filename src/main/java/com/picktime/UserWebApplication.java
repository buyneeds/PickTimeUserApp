package com.picktime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.picktime.repository")
public class UserWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserWebApplication.class, args);
	}
}
