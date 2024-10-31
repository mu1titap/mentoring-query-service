package com.example.section.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MentoringSessionAddAfterDto implements Serializable {
    private String sessionId;

    private String sessionUuid;

    private String mentoringId;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDateTime deadlineDatetime;

    private Integer minHeadCount;

    private Integer maxHeadCount;

    private Integer price;

    private Boolean isClosed;

}