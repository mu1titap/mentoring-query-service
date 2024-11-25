package com.example.section.application;

import com.example.section.messagequeue.messageIn.MentoringAddAfterOutDto;
import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import com.example.section.entity.Mentoring;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MentoringService {

    MentoringResponseDto getMentoringByMentoringUuid(String mentoringUuid);

    List<MentoringResponseDto> findAllByCategoryCodes(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode);

    List<MentoringReusableResponseDto> getReusableMentoringListByMentorUuid(String userUuid);

    // todo : dto로 변환해서 내보내자
    List<Mentoring> findAllByMentorUuidAndIsDeletedFalse(String userUuid);

    @Transactional
    void createMentoringWithSession(MentoringAddAfterOutDto mentoringAddAfterDto);
    @Transactional
    void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto);


}
