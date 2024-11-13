package com.example.section.messagequeue;

import com.example.section.application.MentoringService;
import com.example.section.application.SessionService;
import com.example.section.dto.messageIn.AfterSessionUserOutDto;
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

    private final MentoringService mentoringService;
    private final SessionService sessionService;

    /**
     * 멘토링 생성 이벤트 컨슘
     */
    @KafkaListener(topics = "create-mentoring", groupId = "kafka-mentoring-section-service",
                                                containerFactory = "mentoringAddAfterDtoListener")
    public void createMentoring(MentoringAddAfterOutDto dto) {
        // 멘토링 저장
        mentoringService.createMentoringWithSession(dto);
    }

    /**
     * 멘토링 수정 이벤트 컨슘
     */
    @KafkaListener(topics = "update-mentoring", groupId = "kafka-mentoring-section-service",
            containerFactory = "mentoringEditRequestDtoListener")
    public void createMentoring(MentoringEditRequestOutDto dto) {
        mentoringService.updateMentoring(dto);
    }

    /**
     * 멘토링 세션 참가 등록 이벤트 컨슘
     */
    @KafkaListener(topics = "register-session-user", groupId = "kafka-mentoring-section-service",
            containerFactory = "afterSessionUserOutDtoListener")
    public void registerSessionUser(AfterSessionUserOutDto dto) {
        // 세션 Read data update
        sessionService.updateSessionToSessionUserRegister(dto);
        log.info("멘토링 세션 참가 등록 이벤트 컨슘 후 세션 업데이트 완료");
    }



}

