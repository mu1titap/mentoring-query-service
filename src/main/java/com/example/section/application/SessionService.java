package com.example.section.application;

import com.example.section.dto.messageIn.AfterSessionUserOutDto;
import com.example.section.dto.messageIn.MentoringAddAfterOutDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SessionService {

    MentoringSessionResponseDto findBySessionUuid(String sessionUuid);

    List<MentoringSessionResponseDto> findByMentoringUuidAndDeadlineDate(String mentoringUuid, String userUuid);


    void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto);
}
