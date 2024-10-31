package com.example.section.infrastructure;

import com.example.section.entity.Mentoring;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MentoringMongoRepository extends MongoRepository<Mentoring, String> {

}
