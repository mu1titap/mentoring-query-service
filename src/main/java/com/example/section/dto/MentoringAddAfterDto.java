package com.example.section.dto;

import com.example.section.entity.Mentoring;
import com.example.section.entity.MentoringSession;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MentoringAddAfterDto implements Serializable {
    private String mentoringId;

    private String mentoringUuid;

    private String name;

    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    private List<MentoringSessionAddAfterDto> mentoringSessionAddAfterDtoList;

    public static Mentoring toMongoEntity(MentoringAddAfterDto dto) {
        return Mentoring.builder()
                .mentoringId(dto.getMentoringId())
                .mentoringUuid(dto.mentoringUuid)
                .name(dto.getName())
                .detail(dto.getDetail())
                .mentorUuid(dto.mentorUuid)
                .thumbnailUrl(dto.getThumbnailUrl())
                .isReusable(dto.getIsReusable())
                .isDeleted(dto.getIsDeleted())
                .build();
    }

    public static List<MentoringSession> toMongoMentoringSessionsEntities(
                    List<MentoringSessionAddAfterDto> mentoringSessionAddAfterDtoList) {
        return mentoringSessionAddAfterDtoList.stream()
                .map(
                        mentoringSessionAddAfterDto
                                -> MentoringSession.builder()
                                        .sessionId(mentoringSessionAddAfterDto.getSessionId())
                                        .sessionUuid(mentoringSessionAddAfterDto.getSessionUuid())
                                        .mentoringId(mentoringSessionAddAfterDto.getMentoringId())
                                        .startDate(mentoringSessionAddAfterDto.getStartDate())
                                        .endDate(mentoringSessionAddAfterDto.getEndDate())
                                        .startTime(mentoringSessionAddAfterDto.getStartTime())
                                        .endTime(mentoringSessionAddAfterDto.getEndTime())
                                        .deadlineDatetime(mentoringSessionAddAfterDto.getDeadlineDatetime())
                                        .minHeadCount(mentoringSessionAddAfterDto.getMinHeadCount())
                                        .maxHeadCount(mentoringSessionAddAfterDto.getMaxHeadCount())
                                        .price(mentoringSessionAddAfterDto.getPrice())
                                        .isClosed(mentoringSessionAddAfterDto.getIsClosed())
                                        .build()
                )
                .toList();
    }

}