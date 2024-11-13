package com.example.section.application;

import com.example.section.dto.messageIn.MentoringAddAfterOutDto;
import com.example.section.dto.messageIn.MentoringEditRequestOutDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.entity.Mentoring;
import com.example.section.entity.MentoringSession;
import com.example.section.infrastructure.MentoringMongoRepository;
import com.example.section.infrastructure.MentoringSessionMongoRepository;
import com.example.section.infrastructure.custom.CustomMentoringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public void createMentoringWithSession(MentoringAddAfterOutDto dto) {
        // 멘토링+카테고리 저장
        mentoringMongoRepository.save(dto.toMongoMentoringEntity());
        // 멘토링 세션 저장
        List<MentoringSession> mongoSessionEntities = dto.toMongoSessionEntities();
        // 현재모집인원 0 , 세션진행확정 상태 False 로 초기화
        mongoSessionEntities.forEach(MentoringSession::initNowHeadCountAndIsConfirmed);
        mentoringSessionMongoRepository.saveAll(mongoSessionEntities);
    }

    @Override
    public List<MentoringReusableResponseDto> getReusableMentoringListByMentorUuid(String mentorUuid) {
        return customMentoringRepository.getReusableMentoringListByMentorUuid(mentorUuid)
                .stream()
                .map(MentoringReusableResponseDto::toReusableDto)
                .toList();
    }

    @Override
    public List<Mentoring> findAllByMentorUuidAndIsDeletedFalse(String mentorUuid) {
        return customMentoringRepository.findAllByMentorUuidAndIsDeletedFalse(mentorUuid);
    }


    @Override
    public void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto) {
        customMentoringRepository.updateMentoring(mentoringEditRequestOutDto);
    }



}
