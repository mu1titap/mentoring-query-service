package com.example.section.infrastructure;

import com.example.section.entity.EsMentoring;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MentoringElasticRepositoryTest {
    @Autowired
    private MentoringElasticRepository mentoringElasticRepository;


    @Test
    void createEsMentoring() {
        EsMentoring esMentoring = EsMentoring.builder()
                .id("1")
                .name("특급 개발자의 카프카 멘토링")
                .description("2시간 동안 실무 개발자와 함께 카프카를 배워보세요")
                .build();
        mentoringElasticRepository.save(esMentoring);
    }

}