package com.example.section.application;

import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.dto.out.SessionRoomResponseDto;
import com.example.section.messagequeue.messageIn.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SessionService {

    MentoringSessionResponseDto findBySessionUuid(String sessionUuid);

    List<MentoringSessionResponseDto> findByMentoringUuidAndDeadlineDate(String mentoringUuid, String userUuid);

    SessionRoomResponseDto findSessionRoomBySessionUuid(String sessionUuid);

    String getMentorUuidBySessionUuid(String sessionUuid);

    @Transactional
    void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto);
    @Transactional
    void cancelSessionUser(CancelSessionUserMessage dto);
    @Transactional
    void reRegisterSessionUser(ReRegisterSessionUserMessage dto);

    @Transactional
    void addSession(SessionCreatedAfterOutDto dto);

    @Transactional
    void updateSessionConfirmed(SessionConfirmedMessage dto);
}
