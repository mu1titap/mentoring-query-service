package com.example.section.application;

import com.example.section.dto.messageIn.AfterSessionUserOutDto;
import com.example.section.dto.messageIn.MentoringAddAfterOutDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.entity.MentoringSession;
import com.example.section.entity.vo.SessionUser;
import com.example.section.infrastructure.MentoringMongoRepository;
import com.example.section.infrastructure.MentoringSessionMongoRepository;
import com.example.section.infrastructure.custom.CustomSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class SessionServiceImpl implements  SessionService {
    private final MentoringSessionMongoRepository mentoringSessionMongoRepository;
    private final CustomSessionRepository customSessionRepository;
    @Override
    public MentoringSessionResponseDto findBySessionUuid(String sessionUuid) {
        return MentoringSessionResponseDto.from(mentoringSessionMongoRepository.findBySessionUuid(sessionUuid));
    }

    @Override
    public List<MentoringSessionResponseDto> findByMentoringUuidAndDeadlineDate(String mentoringUuid, String userUuid) {
        List<MentoringSession> sessionList = customSessionRepository.findAllByMentoringUuidAndDeadlineDate(mentoringUuid);
        List<MentoringSessionResponseDto> result = new ArrayList<>();

        boolean isParticipating = false;
        // 세션들을 돌면서 유저가 참여중인 세션인지 체크 한 뒤 Dto 로 변환
        log.info("조회세션 카운트 = "+ sessionList.size());
        for (MentoringSession session : sessionList) {
            List<SessionUser> sessionUserList = session.getSessionUsers();
            if(userUuid != null && sessionUserList != null) { // userUuid 로 참여중인 세션인지 확인
                for (SessionUser sessionUser : sessionUserList) {
                    log.info("target = "+ sessionUser.getUserUuid() +" : " + userUuid);
                    if (sessionUser.getUserUuid().equals(userUuid)) {
                        log.info("세션 참여중인 유저 발견");
                        isParticipating = true;
                        result.add(setUserParticipatingInfo(session, isParticipating));
                        break;
                    }
                }
            }
            else result.add(setUserParticipatingInfo(session, isParticipating));
        }

        return result;

    }


    @Override
    public void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto) {
         customSessionRepository.updateSessionToSessionUserRegister(dto);
    }

    private MentoringSessionResponseDto setUserParticipatingInfo(MentoringSession session, boolean isParticipating) {
        return MentoringSessionResponseDto.builder()
                .sessionUuid(session.getSessionUuid())
                .mentoringUuid(session.getMentoringUuid())
                .startDate(session.getStartDate())
                .endDate(session.getEndDate())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .deadlineDate(session.getDeadlineDate())
                .minHeadCount(session.getMinHeadCount())
                .maxHeadCount(session.getMaxHeadCount())
                .nowHeadCount(session.getNowHeadCount())
                .isParticipating(isParticipating)
                .price(session.getPrice())
                .isClosed(session.getIsClosed())
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }

}
