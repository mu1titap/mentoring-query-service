package com.example.section.application;

import com.example.section.dto.out.CorrectedSearchResultResponseDto;
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

public interface MentoringService {

    MentoringResponseDto getMentoringByMentoringUuid(String mentoringUuid);

    List<MentoringResponseDto> findAllByCategoryCodes(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode);
    Page<MentoringCoreInfoResponseDto> searchByCategoryCodesPagination(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode, Pageable pageable);

    List<MentoringReusableResponseDto> getReusableMentoringListByMentorUuid(String userUuid);


    List<MentoringCoreInfoResponseDto> findAllByMentorUuidAndIsDeletedFalse(String userUuid , Boolean isMentor);
    Page<MentoringCoreInfoResponseDto> searchMentoringByMentorUuidPagination(String userUuid, Boolean isMentor, Pageable pageable);

    Page<MentoringCoreInfoResponseDto> searchByNamePagination(String name, Pageable pageable);
    CorrectedSearchResultResponseDto elasticSearchByNamePagination(String inputWord, Pageable pageable);

    // 인기 벤토링
    List<MentoringCoreInfoResponseDto> findPopularMentoringList(List<String> topCategoryCodeList);

    //List<>

    void createMentoringWithSession(MentoringAddAfterOutDto mentoringAddAfterDto);
    void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto);

    void increaseNowSessionCount(String mentoringUuid, int count);
    void decreaseNowSessionCountByUuid(String mentoringUuid, int count);

    void decreaseNowSessionCountById(String mentoringId, int count);

    void updateMentoringOverview(MentoringOverviewDto dto);

}
