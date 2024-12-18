package com.example.section.entity;

import com.example.section.messagequeue.messageIn.AfterHashtag;
import com.example.section.messagequeue.messageIn.MentoringCategoryAfterOutDto;
import com.example.section.messagequeue.messageIn.MentoringHashTagAfterOutDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "mentoring")
@ToString
public class Mentoring {
    @Id
    private String id;

    private String mentoringId;

    private String mentoringUuid;

    private String name;
    private String description;
    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    // 집계 관련 start
    private Long totalReviewCount;
    private Double reviewStarAvg;
    private Long totalSaleCount;
    private Double totalScore;
    // 집계 관련 end

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer nowSessionCount;
    private List<MentoringCategoryAfterOutDto> mentoringCategoryList;
    private List<AfterHashtag> mentoringHashTagList;

}
