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

    public Mentoring toMongoMentoringEntity() {
        return Mentoring.builder()
                .mentoringId(this.getMentoringId())
                .mentoringUuid(this.getMentoringUuid())
                .name(this.getName())
                .detail(this.getDetail())
                .mentorUuid(this.getMentorUuid())
                .thumbnailUrl(this.getThumbnailUrl())
                .isReusable(this.getIsReusable())
                .isDeleted(this.getIsDeleted())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .mentoringCategoryList(this.getMentoringCategoryAfterOutDtoList())
                .build();
    }
    public List<MentoringSession> toMongoSessionEntities() {
        return this.getMentoringSessionAddAfterOutDtoList()
                .stream()
                .map(session -> MentoringSession.builder()
                        .sessionUuid(session.getSessionUuid())
                        .sessionId(session.getSessionId())
                        .mentoringId(this.getMentoringId())
                        .mentoringUuid(this.getMentoringUuid())
                        .startDate(session.getStartDate())
                        .endDate(session.getEndDate())
                        .startTime(session.getStartTime())
                        .endTime(session.getEndTime())
                        .deadlineDate(session.getDeadlineDate())
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
