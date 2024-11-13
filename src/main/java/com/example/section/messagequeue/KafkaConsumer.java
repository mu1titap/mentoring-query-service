package com.example.section.messagequeue;

import com.example.section.application.MentoringQueryService;
import com.example.section.dto.messageIn.MentoringAddAfterOutDto;
import com.example.section.dto.messageIn.MentoringEditRequestOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MentoringQueryService mentoringQueryService;

    // 멘토링 생성
    @KafkaListener(topics = "create-mentoring", groupId = "kafka-mentoring-section-service",
                                                containerFactory = "mentoringAddAfterDtoListener")
    public void createMentoring(MentoringAddAfterOutDto dto) {
        // 멘토링 저장
        mentoringQueryService.createMentoringWithSession(dto);
        log.info("멘토링 저장 완료");
    }

    // 멘토링 수정
    @KafkaListener(topics = "update-mentoring", groupId = "kafka-mentoring-section-service",
            containerFactory = "mentoringEditRequestDtoListener")
    public void createMentoring(MentoringEditRequestOutDto dto) {
        log.info("edit dto : "+dto);
        log.info("edit dto category : "+dto.getCategoryList());
        mentoringQueryService.updateMentoring(dto);
    }

}

