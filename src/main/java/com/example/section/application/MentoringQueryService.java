package com.example.section.application;

import com.example.section.dto.messageIn.MentoringAddAfterOutDto;
import com.example.section.dto.messageIn.MentoringEditRequestOutDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.entity.Mentoring;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MentoringQueryService {

    MentoringResponseDto getMentoringByMentoringUuid(String mentoringUuid);

    MentoringSessionResponseDto findBySessionUuid(String sessionUuid);

    List<MentoringSessionResponseDto> findAllByMentoringUuid(String mentoringUuid);

    List<MentoringReusableResponseDto> getReusableMentoringListByMentorUuid(String mentorUuid);

    List<Mentoring> findAllByMentorUuidAndIsDeletedFalse(String mentorUuid);

    @Transactional
    void createMentoringWithSession(MentoringAddAfterOutDto mentoringAddAfterDto);
    @Transactional
    void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto);


}
