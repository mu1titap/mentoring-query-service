package com.example.section.infrastructure.custom;

import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.messagequeue.messageIn.AfterSessionUserOutDto;
import com.example.section.messagequeue.messageIn.CancelSessionUserMessage;
import com.example.section.messagequeue.messageIn.ReRegisterSessionUserMessage;
import com.example.section.entity.MentoringSession;
import com.example.section.messagequeue.messageIn.SessionConfirmedMessage;

import java.util.List;

public interface CustomSessionRepository {
    void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto);

    void cancelSessionUser(CancelSessionUserMessage dto);
    void reRegisterSessionUser(ReRegisterSessionUserMessage dto);

    List<MentoringSession> findAllByMentoringUuidAndDeadlineDate(String mentoringUuid);
    List<MentoringSessionResponseDto> findAllByMentoringUuidAndDeadlineDateV2(String mentoringUuid, String userUuid);

    void updateSessionConfirmed(SessionConfirmedMessage dto);

}
