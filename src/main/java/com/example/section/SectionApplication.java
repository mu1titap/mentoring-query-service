package com.example.section;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SectionApplication.class, args);
	}

}
