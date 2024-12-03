package com.example.section.application;

import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.dto.out.SessionListResponseDto;
import com.example.section.dto.out.SessionRoomResponseDto;
import com.example.section.entity.MentoringSession;
import com.example.section.entity.vo.SessionUser;
import com.example.section.infrastructure.MentoringSessionMongoRepository;
import com.example.section.infrastructure.custom.CustomSessionRepository;
import com.example.section.messagequeue.messageIn.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    /**
     * 1. mentoringUuid 으로 세션 리스트 출력
     * 2. userUuid 로 참여중인 세션 확인
     */
    @Override
    public List<MentoringSessionResponseDto> findByMentoringUuidAndDeadlineDate(String mentoringUuid, String userUuid) {
        List<MentoringSession> sessionList = customSessionRepository.findAllByMentoringUuidAndDeadlineDate(mentoringUuid);

        List<MentoringSessionResponseDto> result = new ArrayList<>();
        boolean isParticipating;
        // 세션들을 돌면서 유저가 참여중인 세션인지 체크 한 뒤 Dto 로 변환
        for (MentoringSession session : sessionList) {
            isParticipating = false;
            // 세션의 참가자 리스트
            List<SessionUser> sessionUserList = session.getSessionUsers();
            if(userUuid != null && sessionUserList != null && !sessionUserList.isEmpty()) { // userUuid 로 참여중인 세션인지 확인
                for (SessionUser sessionUser : sessionUserList) {
                    if (sessionUser.getUserUuid().equals(userUuid)) {
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
    public List<SessionListResponseDto> findByMentoringUuidAndDeadlineDateV2(String mentoringUuid, String userUuid) {
        List<MentoringSessionResponseDto> sessionList = customSessionRepository.findAllByMentoringUuidAndDeadlineDateV2(mentoringUuid,userUuid);
        //SessionListResponseDto result = new SessionListResponseDto();
        //result.setMentoringSessionResponseDtoList(sessionList);
        // startDate 별로 Map 으로 묶어서 반환
        Map<LocalDate, List<MentoringSessionResponseDto>> sessionsByDate = sessionList.stream()
                .collect(Collectors.groupingBy(MentoringSessionResponseDto::getStartDate));

        return sessionsByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> SessionListResponseDto.builder()
                        .totalCount(entry.getValue().size())
                        .startDate(entry.getKey())
                        .mentoringSessionResponseDtoList(entry.getValue())
                        .build())
                .collect(Collectors.toList());




    }

    private void collect(Collector<Object,?, List<Object>> list) {
    }

    @Override
    public SessionRoomResponseDto findSessionRoomBySessionUuid(String sessionUuid) {
        return SessionRoomResponseDto.from(mentoringSessionMongoRepository.findBySessionUuid(sessionUuid));
    }

    @Override
    public String getMentorUuidBySessionUuid(String sessionUuid) {
        MentoringSession session = mentoringSessionMongoRepository.findBySessionUuid(sessionUuid);
        return session != null ? session.getMentorUuid() : null;
    }


    @Override
    public void updateSessionToSessionUserRegister(AfterSessionUserOutDto dto) {
         customSessionRepository.updateSessionToSessionUserRegister(dto);
    }

    @Override
    public void cancelSessionUser(CancelSessionUserMessage dto) {
        customSessionRepository.cancelSessionUser(dto);
    }

    @Override
    public void reRegisterSessionUser(ReRegisterSessionUserMessage dto) {
        customSessionRepository.reRegisterSessionUser(dto);
    }

    @Override
    public void addSession(SessionCreatedAfterOutDto dto) {
        // 멘토링 read data update
        int addSessionCount = dto.getSessionAddAfterOutDtos().size();

        // 세션 read date save
        List<MentoringSession> sessionEntities = dto.toSessionEntities();
        sessionEntities.forEach(MentoringSession::initNowHeadCountAndIsConfirmed);
        mentoringSessionMongoRepository.saveAll(sessionEntities);
    }

    @Override
    public void updateSessionConfirmed(SessionConfirmedMessage dto) {
        customSessionRepository.updateSessionConfirmed(dto);
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
                //.createdAt(session.getCreatedAt())
                //.updatedAt(session.getUpdatedAt())
                .build();
    }

}
