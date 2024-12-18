package com.example.section.dto.out;

import com.example.section.entity.Mentoring;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentoringCoreInfoResponseDto {
    private String mentoringUuid;
    private Long reviewCount; // 리뷰 수
    private Double averageStar;// 리뷰 별점 평균
    private Long totalSaleCount;// 총 판매 수
    private String name;
    private String description;
    private String thumbnailUrl;
    private Boolean isAvailable;
    private Integer nowSessionCount;


}
