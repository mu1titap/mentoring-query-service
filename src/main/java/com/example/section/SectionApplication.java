package com.example.section;

import com.example.section.elasticSearch.infrastructure.MentoringElasticRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableMongoRepositories(excludeFilters = @ComponentScan.Filter(
//		type = FilterType.ASSIGNABLE_TYPE,
//		classes = MentoringElasticRepository.class))
@SpringBootApplication
@EnableMongoAuditing
public class SectionApplication {
//
	public static void main(String[] args) {
		SpringApplication.run(SectionApplication.class, args);
	}

}
