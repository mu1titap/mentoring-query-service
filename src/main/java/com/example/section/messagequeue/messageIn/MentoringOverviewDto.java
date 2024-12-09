package com.example.section.messagequeue.messageIn;

import lombok.Data;

@Data
public class MentoringOverviewDto {
    private String mentoringUuid;

    private Double reviewScore;
    private Long totalReviewCount;
    private Double reviewStarAvg;
    private Double saleScore;
    private Long totalSaleCount;
    private Double totalScore;
}