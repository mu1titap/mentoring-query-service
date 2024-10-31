package com.example.section.infrastructure;

import com.example.section.entity.MentoringSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MentoringSessionMongoRepository extends MongoRepository<MentoringSession, String> {

}
