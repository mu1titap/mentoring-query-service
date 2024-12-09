package com.example.section.application;

import com.example.section.dto.out.MentoringCoreInfoResponseDto;
import com.example.section.messagequeue.messageIn.MentoringAddAfterOutDto;
import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import com.example.section.entity.Mentoring;
import com.example.section.messagequeue.messageIn.MentoringOverviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MentoringService {

    MentoringResponseDto getMentoringByMentoringUuid(String mentoringUuid);

    List<MentoringResponseDto> findAllByCategoryCodes(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode);
    Page<MentoringCoreInfoResponseDto> searchByCategoryCodesPagination(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode, Pageable pageable);

    List<MentoringReusableResponseDto> getReusableMentoringListByMentorUuid(String userUuid);


    List<MentoringCoreInfoResponseDto> findAllByMentorUuidAndIsDeletedFalse(String userUuid , Boolean isMentor);
    Page<MentoringCoreInfoResponseDto> searchMentoringByMentorUuidPagination(String userUuid, Boolean isMentor, Pageable pageable);

    Page<MentoringCoreInfoResponseDto> searchByNamePagination(String name, Pageable pageable);

    // 인기 벤토링
    List<MentoringCoreInfoResponseDto> findPopularMentoringList(List<String> topCategoryCodeList);

    //List<>

    @Transactional
    void createMentoringWithSession(MentoringAddAfterOutDto mentoringAddAfterDto);
    @Transactional
    void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto);

    @Transactional
    void increaseNowSessionCount(String mentoringUuid, int count);
    @Transactional
    void decreaseNowSessionCountByUuid(String mentoringUuid, int count);

    @Transactional
    void decreaseNowSessionCountById(String mentoringId, int count);

    @Transactional
    void updateMentoringOverview(MentoringOverviewDto dto);

}
