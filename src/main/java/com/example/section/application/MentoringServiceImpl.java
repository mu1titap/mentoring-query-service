package com.example.section.application;

import com.example.section.dto.out.MentoringCoreInfoResponseDto;
import com.example.section.messagequeue.messageIn.MentoringAddAfterOutDto;
import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import com.example.section.entity.Mentoring;
import com.example.section.entity.MentoringSession;
import com.example.section.infrastructure.MentoringMongoRepository;
import com.example.section.infrastructure.MentoringSessionMongoRepository;
import com.example.section.infrastructure.custom.CustomMentoringRepository;
import com.example.section.messagequeue.messageIn.ReviewStarDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MentoringServiceImpl implements MentoringService {
    private final MentoringMongoRepository  mentoringMongoRepository;
    private final CustomMentoringRepository customMentoringRepository;
    private final MentoringSessionMongoRepository mentoringSessionMongoRepository;
    @Override
    public MentoringResponseDto getMentoringByMentoringUuid(String mentoringUuid) {
        return MentoringResponseDto.fromEntity(mentoringMongoRepository.findByMentoringUuidAndIsDeletedFalse(mentoringUuid));
    }

    @Override
    public List<MentoringResponseDto> findAllByCategoryCodes(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode) {
        return customMentoringRepository.findAllByCategoryCodes(topCategoryCode, middleCategoryCode, bottomCategoryCode)
                .stream()
                .map(MentoringResponseDto::fromEntity)
                .toList();
    }

    @Override
    public Page<MentoringCoreInfoResponseDto> searchByCategoryCodesPagination(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode, Pageable pageable) {
        return customMentoringRepository.searchByCategoryCodesPagination(topCategoryCode, middleCategoryCode, bottomCategoryCode, pageable);
    }

    @Override
    public void createMentoringWithSession(MentoringAddAfterOutDto dto) {

        // 멘토링+카테고리 저장
        int sessionCount = dto.getMentoringSessionAddAfterOutDtoList() == null ? 0 : dto.getMentoringSessionAddAfterOutDtoList().size();
        mentoringMongoRepository.save(dto.toMongoMentoringEntity(sessionCount));
        // 멘토링 세션 저장
        if(dto.getMentoringSessionAddAfterOutDtoList() != null){
            List<MentoringSession> mongoSessionEntities = dto.toMongoSessionEntities();
            // 현재모집인원 0 , 세션진행확정 상태 False 로 초기화
            mongoSessionEntities.forEach(MentoringSession::initNowHeadCountAndIsConfirmed);
            mentoringSessionMongoRepository.saveAll(mongoSessionEntities);
        }

    }

    @Override
    public List<MentoringReusableResponseDto> getReusableMentoringListByMentorUuid(String userUuid) {
        return customMentoringRepository.getReusableMentoringListByMentorUuid(userUuid)
                .stream()
                .map(MentoringReusableResponseDto::toReusableDto)
                .toList();
    }

    @Override
    public List<MentoringCoreInfoResponseDto> findAllByMentorUuidAndIsDeletedFalse(String userUuid, Boolean isMentor) {
        return customMentoringRepository.findAllByMentorUuidAndIsDeletedFalse(userUuid, isMentor);
    }

    @Override
    public Page<MentoringCoreInfoResponseDto> searchMentoringByMentorUuidPagination(String userUuid, Boolean isMentor, Pageable pageable) {
        return customMentoringRepository.searchMentoringByMentorUuidPagination(userUuid, isMentor,  pageable);
    }

    @Override
    public Page<MentoringCoreInfoResponseDto> searchByNamePagination(String name, Pageable pageable) {
        return customMentoringRepository.searchByNamePagination(name, pageable);
    }


    @Override
    public void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto) {
        customMentoringRepository.updateMentoring(mentoringEditRequestOutDto);
    }

    @Override
    public void increaseNowSessionCount(String mentoringUuid, int count) {
        customMentoringRepository.increaseNowSessionCount(mentoringUuid, count);
    }

    @Override
    public void decreaseNowSessionCountByUuid(String mentoringUuid, int count) {
        customMentoringRepository.decreaseNowSessionCountByUuid(mentoringUuid, count);
    }

    @Override
    public void decreaseNowSessionCountById(String mentoringId, int count) {
        customMentoringRepository.decreaseNowSessionCountById(mentoringId, count);
    }

    @Override
    public void updateReviewStar(ReviewStarDto dto) {
        customMentoringRepository.updateReviewStar(dto);
    }


}
