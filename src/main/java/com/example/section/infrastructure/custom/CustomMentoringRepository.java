package com.example.section.infrastructure.custom;

import com.example.section.dto.in.MentoringEditRequestOutDto;
import com.example.section.entity.Mentoring;

import java.util.List;

public interface CustomMentoringRepository {
    public void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestDto);

    public List<Mentoring> getReusableMentoringListByMentorUuid(String mentorUuid);

    // mentorUuid로 삭제되지 않은 멘토링 조회
    List<Mentoring> findAllByMentorUuidAndIsDeletedFalse(String mentorUuid);

}
