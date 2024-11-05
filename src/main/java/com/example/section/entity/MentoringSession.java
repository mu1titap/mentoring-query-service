package com.example.section.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "mentoring_session")
@ToString
public class MentoringSession {
    @Id
    private String id;
    private String sessionUuid;

    private String sessionId;

    private String mentoringId;
    private String mentoringUuid;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDateTime deadlineDatetime;

    private Integer minHeadCount;

    private Integer maxHeadCount;

    private Integer price;

    private Boolean isClosed;
    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
