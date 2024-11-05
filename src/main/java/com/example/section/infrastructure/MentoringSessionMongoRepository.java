package com.example.section.infrastructure;

import com.example.section.entity.MentoringSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MentoringSessionMongoRepository extends MongoRepository<MentoringSession, String> {
    MentoringSession findBySessionUuid(String sessionUuid);

    List<MentoringSession> findAllByMentoringUuid(String mentoringUuid);
}
