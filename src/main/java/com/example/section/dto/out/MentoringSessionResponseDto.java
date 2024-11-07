package com.example.section.dto.out;

import com.example.section.entity.MentoringSession;
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

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate deadlineDate;

    private Integer minHeadCount;

    private Integer maxHeadCount;

    private Integer price;

    private Boolean isClosed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MentoringSessionResponseDto fromEntity(MentoringSession mentoringSession){
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
                .price(mentoringSession.getPrice())
                .isClosed(mentoringSession.getIsClosed())
                .createdAt(mentoringSession.getCreatedAt())
                .updatedAt(mentoringSession.getUpdatedAt())
                .build();
    }
    public static List<MentoringSessionResponseDto> fromEntities(List<MentoringSession> mentoringSessions){
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
                                                .price(mentoringSession.getPrice())
                                                .isClosed(mentoringSession.getIsClosed())
                                                .createdAt(mentoringSession.getCreatedAt())
                                                .updatedAt(mentoringSession.getUpdatedAt())
                                                .build()
                )
                .toList();
    }
}
