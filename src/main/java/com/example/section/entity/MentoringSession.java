package com.example.section.entity;

import com.example.section.entity.vo.SessionUser;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    private LocalDate deadlineDate;

    private Integer minHeadCount;
    private Integer maxHeadCount;
    private Integer nowHeadCount;

    private Integer price;

    private Boolean isClosed;
    private Boolean isDeleted;
    private Boolean isConfirmed; // 확정 여부

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<SessionUser> sessionUsers; // 세션 유저 리스트

    public void initNowHeadCountAndIsConfirmed() {
        this.nowHeadCount = 0;
        this.isConfirmed = false;
    }
}
