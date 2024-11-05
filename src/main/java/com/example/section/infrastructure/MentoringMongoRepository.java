package com.example.section.infrastructure;

import com.example.section.entity.Mentoring;
import com.example.section.entity.MentoringSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MentoringMongoRepository extends MongoRepository<Mentoring, String> {
    // mentoringUuid로  멘토링 조회
    Mentoring findByMentoringUuid(String mentoringUuid);

    // mentoringUuid로 삭제되지 않은 멘토링 조회
    Mentoring findByMentoringUuidAndIsDeletedFalse(String mentoringUuid);

}
