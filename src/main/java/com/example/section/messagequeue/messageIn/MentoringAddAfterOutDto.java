package com.example.section.messagequeue.messageIn;

import com.example.section.entity.Mentoring;
import com.example.section.entity.MentoringSession;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"mentoringSessionAddAfterOutDtoList", "mentoringCategoryAfterOutDtoList", "mentoringHashTagAfterOutDto"})
//@ToString
public class MentoringAddAfterOutDto {
    private String mentoringId;

    private String mentoringUuid;

    private String name;
    private String description;
    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MentoringSessionAddAfterOutDto> mentoringSessionAddAfterOutDtoList;

    private List<MentoringCategoryAfterOutDto> mentoringCategoryAfterOutDtoList;

    private MentoringHashTagAfterOutDto mentoringHashTagAfterOutDto;

    public Mentoring toMongoMentoringEntity(Integer sessionCount) {
        return Mentoring.builder()
                .mentoringId(this.getMentoringId())
                .mentoringUuid(this.getMentoringUuid())
                .name(this.getName())
                .description(this.getDescription())
                .detail(this.getDetail())
                .mentorUuid(this.getMentorUuid())
                .thumbnailUrl(this.getThumbnailUrl())
                .isReusable(this.getIsReusable())
                .isDeleted(this.getIsDeleted())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .nowSessionCount(sessionCount)
                .mentoringCategoryList(this.getMentoringCategoryAfterOutDtoList()!=null?
                        this.getMentoringCategoryAfterOutDtoList() : null)
                .mentoringHashTagList(this.getMentoringHashTagAfterOutDto()!=null?
                        this.getMentoringHashTagAfterOutDto().getAfterHashtagList() : null)
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
                        .mentoringName(this.getName())
                        .mentorUuid(this.getMentorUuid())
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
