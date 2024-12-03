package com.example.section.messagequeue.messageIn;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SessionConfirmedMessage {
    private String mentoringId;
    private String mentorUuid;
    private String sessionUuid;
    private Boolean sessionIsConfirmed;
    private LocalDate startDate;

    private Integer minHeadCount;
    private Integer maxHeadCount;
    private Integer nowHeadCount;
}
