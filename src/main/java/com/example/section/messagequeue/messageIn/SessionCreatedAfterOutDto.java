package com.example.section.messagequeue.messageIn;

import com.example.section.entity.MentoringSession;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "sessionAddAfterOutDtos")
public class SessionCreatedAfterOutDto {
    private String mentoringId;
    private String mentoringUuid;

    private String mentorUuid;
    private String mentoringName;

    List<SessionAddAfterOutDto> sessionAddAfterOutDtos;

    public List<MentoringSession> toSessionEntities() {
        return sessionAddAfterOutDtos.stream()
                .map(sessionAddAfterOutDto -> MentoringSession.builder()
                        .mentoringId(mentoringId)
                        .mentoringUuid(mentoringUuid)
                        .sessionId(sessionAddAfterOutDto.getSessionId())
                        .sessionUuid(sessionAddAfterOutDto.getSessionUuid())
                        .startDate(sessionAddAfterOutDto.getStartDate())
                        .endDate(sessionAddAfterOutDto.getEndDate())
                        .startTime(sessionAddAfterOutDto.getStartTime())
                        .endTime(sessionAddAfterOutDto.getEndTime())
                        .deadlineDate(sessionAddAfterOutDto.getDeadlineDate())
                        .minHeadCount(sessionAddAfterOutDto.getMinHeadCount())
                        .maxHeadCount(sessionAddAfterOutDto.getMaxHeadCount())
                        .price(sessionAddAfterOutDto.getPrice())
                        .isClosed(sessionAddAfterOutDto.getIsClosed())
                        .isDeleted(sessionAddAfterOutDto.getIsDeleted())
                        .build())
                .toList();
    }
}
