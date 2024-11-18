package com.example.section.application;

import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.messagequeue.messageIn.AfterSessionUserOutDto;
import com.example.section.messagequeue.messageIn.CancelSessionUserMessage;
import com.example.section.messagequeue.messageIn.ReRegisterSessionUserMessage;
import com.example.section.messagequeue.messageIn.SessionCreatedAfterOutDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SessionService {

    MentoringSessionResponseDto findBySessionUuid(String sessionUuid);

    List<MentoringSessionResponseDto> findByMentoringUuidAndDeadlineDate(String mentoringUuid, String userUuid);

    @Transactional
    void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto);
    @Transactional
    void cancelSessionUser(CancelSessionUserMessage dto);
    @Transactional
    void reRegisterSessionUser(ReRegisterSessionUserMessage dto);

    @Transactional
    void addSession(SessionCreatedAfterOutDto dto);
}
