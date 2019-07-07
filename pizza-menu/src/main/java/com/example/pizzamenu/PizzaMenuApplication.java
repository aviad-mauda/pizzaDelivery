package com.example.pizzamenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PizzaMenuApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaMenuApplication.class, args);
	}

}
