package com.example.section.dto.out;

import com.example.section.entity.MentoringSession;
import com.example.section.entity.vo.SessionUser;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MentoringSessionResponseDto {

    private String sessionUuid;

    private String mentoringUuid;

    // todo : 멘토 uuid 추가

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate deadlineDate;

    private Integer minHeadCount;
    private Integer maxHeadCount;
    private Integer nowHeadCount;
    private Boolean isParticipating; // 유저의 참가여부
    private Integer price;


    private Boolean isClosed;

    private List<SessionUserResponseDto> sessionUserList;


    public static MentoringSessionResponseDto from(MentoringSession mentoringSession){
        return MentoringSessionResponseDto.builder()
                .sessionUuid(mentoringSession.getSessionUuid())
                .mentoringUuid(mentoringSession.getMentoringUuid())
                .startDate(mentoringSession.getStartDate())
                .endDate(mentoringSession.getEndDate())
                .startTime(mentoringSession.getStartTime())
                .endTime(mentoringSession.getEndTime())
                .deadlineDate(mentoringSession.getDeadlineDate())
                .minHeadCount(mentoringSession.getMinHeadCount())
                .maxHeadCount(mentoringSession.getMaxHeadCount())
                .nowHeadCount(mentoringSession.getNowHeadCount())
                .price(mentoringSession.getPrice())
                .isClosed(mentoringSession.getIsClosed())
//                .createdAt(mentoringSession.getCreatedAt())
//                .updatedAt(mentoringSession.getUpdatedAt())
                .build();
    }
    public static List<MentoringSessionResponseDto> from(List<MentoringSession> mentoringSessions){
        return mentoringSessions.stream()
                .map(
                        mentoringSession -> MentoringSessionResponseDto
                                                .builder()
                                                .sessionUuid(mentoringSession.getSessionUuid())
                                                .mentoringUuid(mentoringSession.getMentoringUuid())
                                                .startDate(mentoringSession.getStartDate())
                                                .endDate(mentoringSession.getEndDate())
                                                .startTime(mentoringSession.getStartTime())
                                                .endTime(mentoringSession.getEndTime())
                                                .deadlineDate(mentoringSession.getDeadlineDate())
                                                .minHeadCount(mentoringSession.getMinHeadCount())
                                                .maxHeadCount(mentoringSession.getMaxHeadCount())
                                                .nowHeadCount(mentoringSession.getNowHeadCount())
                                                .price(mentoringSession.getPrice())
                                                .isClosed(mentoringSession.getIsClosed())
                                                //.createdAt(mentoringSession.getCreatedAt())
                                                //.updatedAt(mentoringSession.getUpdatedAt())
                                                .build()
                )
                .toList();
    }
}
