package com.example.section.messagequeue;

import com.example.section.application.MentoringService;
import com.example.section.application.SessionService;
import com.example.section.messagequeue.messageIn.*;
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
    @KafkaListener(topics = "create-mentoring", groupId = "kafka-mentoring-query-service",
                                                containerFactory = "mentoringAddAfterDtoListener")
    public void createMentoring(MentoringAddAfterOutDto dto) {
        log.info("멘토링 생성 이벤트 컨슘 + dto = "+dto);
        // 멘토링 저장
        mentoringService.createMentoringWithSession(dto);
    }
    /**
     * 세션 추가 이벤트 컨슘
     */
    @KafkaListener(topics = "add-session", groupId = "kafka-mentoring-query-service",
            containerFactory = "addSessionUserOutDtoListener")
    public void addSession(SessionCreatedAfterOutDto dto) {
        String mentoringUuid = dto.getMentoringUuid();
        int increaseNowSessionCount = dto.getSessionAddAfterOutDtos().size();
        // 멘토링 read data 현재 세션카운트 증가
        mentoringService.increaseNowSessionCount(mentoringUuid, increaseNowSessionCount);
        // 세션 read data 저장
        sessionService.addSession(dto);
    }

    /**
     * 멘토링 수정 이벤트 컨슘
     */
    @KafkaListener(topics = "update-mentoring", groupId = "kafka-mentoring-query-service",
            containerFactory = "mentoringEditRequestDtoListener")
    public void createMentoring(MentoringEditRequestOutDto dto) {
        mentoringService.updateMentoring(dto);
    }

    /**
     * 멘토링 세션 참가 등록 이벤트 컨슘
     */
    @KafkaListener(topics = "register-session-user", groupId = "kafka-mentoring-query-service",
            containerFactory = "afterSessionUserOutDtoListener")
    public void registerSessionUser(AfterSessionUserOutDto dto) {
        // 세션 Read data update
        sessionService.updateSessionToSessionUserRegister(dto);
        log.info("멘토링 세션 참가 등록 이벤트 컨슘 후 세션 업데이트 완료");
    }

    /**
     * 멘토링 세션 참가 등록 '취소' 이벤트 컨슘
     */
    @KafkaListener(topics = "cancel-session-user", groupId = "kafka-mentoring-query-service",
            containerFactory = "cancelSessionUserOutDtoListener")
    public void cancelSessionUser(CancelSessionUserMessage dto) {
        sessionService.cancelSessionUser(dto);
        log.info("멘토링 세션 참가 등록 '취소' 이벤트 업데이트 완료");
    }

    /**
     * 멘토링 세션 '재'참가 등록  이벤트 컨슘
     */
    @KafkaListener(topics = "re-register-session-user", groupId = "kafka-mentoring-query-service",
            containerFactory = "reRegisterSessionUserOutDtoListener")
    public void reRegisterSessionUser(ReRegisterSessionUserMessage dto) {
        sessionService.reRegisterSessionUser(dto);
        log.info("멘토링 세션 참가 등록 '취소' 이벤트 업데이트 완료");
    }

    /**
     * 세션 확정
     */
    @KafkaListener(topics = "update-session-confirmed", groupId = "kafka-mentoring-query-service",
            containerFactory = "sessionConfirmedListener")
    public void updateSessionConfirmed(SessionConfirmedMessage dto) {
        // 세션 확정되면 현재 세션카운트 감소 (확정이든 취소든 세션 예약 못하는 거라 똑같이 감소)
        mentoringService.decreaseNowSessionCountById(dto.getMentoringId(), 1);
        sessionService.updateSessionConfirmed(dto);
    }





}

