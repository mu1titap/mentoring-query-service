package com.example.section.messagequeue.messageIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewStarDto {
    private String mentoringUuid;
    private String mentorUuid;
    private Long reviewCount;
    private Double averageStar;
    private Long totalStar;

}