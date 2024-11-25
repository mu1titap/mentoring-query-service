package com.example.section.infrastructure.custom;

import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
import com.example.section.entity.Mentoring;

import java.util.List;

public interface CustomMentoringRepository {
    void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestDto);

    List<Mentoring> getReusableMentoringListByMentorUuid(String userUuid);

    // mentorUuid로 삭제되지 않은 멘토링 조회
    List<Mentoring> findAllByMentorUuidAndIsDeletedFalse(String userUuid);

    List<Mentoring> findAllByCategoryCodes(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode);

}
