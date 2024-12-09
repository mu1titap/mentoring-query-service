package com.example.section.infrastructure.custom;

import com.example.section.dto.out.MentoringCoreInfoResponseDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
import com.example.section.entity.Mentoring;
import com.example.section.messagequeue.messageIn.MentoringOverviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomMentoringRepository {
    void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestDto);

    void increaseNowSessionCount(String mentoringUuid, int count);
    void decreaseNowSessionCountByUuid(String mentoringUuid, int count);
    void decreaseNowSessionCountById(String mentoringId, int count);

    void updateMentoringOverview(MentoringOverviewDto dto);

    List<Mentoring> getReusableMentoringListByMentorUuid(String userUuid);

    // mentorUuid로 삭제되지 않은 멘토링 조회
    List<MentoringCoreInfoResponseDto> findAllByMentorUuidAndIsDeletedFalse(String userUuid, Boolean isMentor);
    Page<MentoringCoreInfoResponseDto> searchMentoringByMentorUuidPagination(String userUuid, Boolean isMentor, Pageable pageable);

    List<Mentoring> findAllByCategoryCodes(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode);

    Page<MentoringCoreInfoResponseDto> searchByCategoryCodesPagination(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode, Pageable pageable);

    Page<MentoringCoreInfoResponseDto> searchByNamePagination(String name, Pageable pageable);

    List<MentoringCoreInfoResponseDto> findPopularMentoringList(List<String> topCategoryCodeList);




}
