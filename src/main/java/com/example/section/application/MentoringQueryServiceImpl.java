package com.example.section.application;

import com.example.section.dto.in.MentoringAddAfterOutDto;
import com.example.section.dto.in.MentoringEditRequestOutDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.entity.Mentoring;
import com.example.section.infrastructure.MentoringMongoRepository;
import com.example.section.infrastructure.MentoringSessionMongoRepository;
import com.example.section.infrastructure.custom.CustomMentoringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MentoringQueryServiceImpl implements  MentoringQueryService {
    private final MentoringMongoRepository  mentoringMongoRepository;
    private final MentoringSessionMongoRepository mentoringSessionMongoRepository;
    private final CustomMentoringRepository customMentoringRepository;
    @Override
    public MentoringResponseDto getMentoringByMentoringUuid(String mentoringUuid) {
        return MentoringResponseDto.fromEntity(mentoringMongoRepository.findByMentoringUuidAndIsDeletedFalse(mentoringUuid));
    }

    @Override
    public MentoringSessionResponseDto findBySessionUuid(String sessionUuid) {
        return MentoringSessionResponseDto
                    .fromEntity(mentoringSessionMongoRepository.findBySessionUuid(sessionUuid));
    }

    @Override
    public List<MentoringSessionResponseDto> findAllByMentoringUuid(String mentoringUuid) {
        List<MentoringSessionResponseDto> mentoringSessionResponseDtos = MentoringSessionResponseDto
                .fromEntities(mentoringSessionMongoRepository.findAllByMentoringUuid(mentoringUuid));
        log.info("멘토링 세션 리스트 조회 : "+mentoringSessionResponseDtos.size());
        mentoringSessionResponseDtos.forEach(log::info);
        return mentoringSessionResponseDtos;
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
    public void createMentoringWithSession(MentoringAddAfterOutDto dto) {
        // 멘토링+카테고리 저장
        mentoringMongoRepository.save(dto.toMongoMentoringEntity());
        // 멘토링 세션 저장
        log.info("멘토링 세션 저장 : "+dto.getMentoringSessionAddAfterOutDtoList());
        mentoringSessionMongoRepository.saveAll(dto.toMongoSessionEntities());
    }
    @Override
    public void updateMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto) {
        customMentoringRepository.updateMentoring(mentoringEditRequestOutDto);
    }



}
