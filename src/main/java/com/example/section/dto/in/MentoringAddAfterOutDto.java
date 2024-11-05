package com.example.section.dto.in;

import com.example.section.entity.Mentoring;
import com.example.section.entity.MentoringSession;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "mentoringSessionAddAfterOutDtoList")
//@ToString
public class MentoringAddAfterOutDto {
    private String mentoringId;

    private String mentoringUuid;

    private String name;

    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MentoringSessionAddAfterOutDto> mentoringSessionAddAfterOutDtoList;

    private List<MentoringCategoryAfterOutDto> mentoringCategoryAfterOutDtoList;

    public static Mentoring toMongoMentoringEntity(MentoringAddAfterOutDto dto) {
        return Mentoring.builder()
                .mentoringId(dto.getMentoringId())
                .mentoringUuid(dto.getMentoringUuid())
                .name(dto.getName())
                .detail(dto.getDetail())
                .mentorUuid(dto.getMentorUuid())
                .thumbnailUrl(dto.getThumbnailUrl())
                .isReusable(dto.getIsReusable())
                .isDeleted(dto.getIsDeleted())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .mentoringCategoryList(dto.getMentoringCategoryAfterOutDtoList())
                .build();
    }
    public static List<MentoringSession> toMongoSessionEntities(MentoringAddAfterOutDto dto) {
        return dto.getMentoringSessionAddAfterOutDtoList()
                .stream()
                .map(session -> MentoringSession.builder()
                        .sessionUuid(session.getSessionUuid())
                        .sessionId(session.getSessionId())
                        .mentoringId(dto.getMentoringId())
                        .mentoringUuid(dto.getMentoringUuid())
                        .startDate(session.getStartDate())
                        .endDate(session.getEndDate())
                        .startTime(session.getStartTime())
                        .endTime(session.getEndTime())
                        .deadlineDatetime(session.getDeadlineDatetime())
                        .minHeadCount(session.getMinHeadCount())
                        .maxHeadCount(session.getMaxHeadCount())
                        .price(session.getPrice())
                        .isClosed(session.getIsClosed())
                        .isDeleted(session.getIsDeleted())
                        .createdAt(session.getCreatedAt())
                        .updatedAt(session.getUpdatedAt())
                        .build())
                .toList();
    }
}
