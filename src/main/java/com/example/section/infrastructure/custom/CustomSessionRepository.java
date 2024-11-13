package com.example.section.infrastructure.custom;

import com.example.section.dto.messageIn.AfterSessionUserOutDto;
import com.example.section.dto.messageIn.MentoringEditRequestOutDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.entity.Mentoring;
import com.example.section.entity.MentoringSession;

import java.util.List;

public interface CustomSessionRepository {
    void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto);

    List<MentoringSession> findAllByMentoringUuidAndDeadlineDate(String mentoringUuid);

}
