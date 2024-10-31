package com.example.section.messagequeue;

import com.example.section.dto.MentoringAddAfterDto;
import com.example.section.infrastructure.MentoringMongoRepository;
import com.example.section.infrastructure.MentoringSessionMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MentoringMongoRepository mentoringMongoRepository;
    private final MentoringSessionMongoRepository mentoringSessionMongoRepository;
    @KafkaListener(topics = "create-mentoring", groupId = "kafka-mentoring-section-service",
                                                containerFactory = "mentoringAddAfterDtoListener")
    public void createMentoring(MentoringAddAfterDto dto) {
        // 멘토링 저장
        mentoringMongoRepository.save(MentoringAddAfterDto.toMongoEntity(dto));
        // 멘토링 세션 저장
        mentoringSessionMongoRepository
                .saveAll(MentoringAddAfterDto.toMongoMentoringSessionsEntities(dto.getMentoringSessionAddAfterDtoList()));
    }


}

