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
    private Long reviewCount;
    private Double averageStar;
    private String name;
    private String description;
    private String thumbnailUrl;
    private Boolean isAvailable;
    private Integer nowSessionCount;

    public static MentoringCoreInfoResponseDto from (Mentoring mentoring) {
        return MentoringCoreInfoResponseDto.builder()
                .mentoringUuid(mentoring.getMentoringUuid())
                .name(mentoring.getName())
                .description(mentoring.getDescription())
                .thumbnailUrl(mentoring.getThumbnailUrl())
                .isAvailable(mentoring.getNowSessionCount()!=null && mentoring.getNowSessionCount() > 0)
                .nowSessionCount(mentoring.getNowSessionCount())
                .reviewCount(mentoring.getReviewCount() == null ? 0 : mentoring.getReviewCount())
                .averageStar(mentoring.getAverageStar() == null ? 0 : mentoring.getAverageStar())
                .build();
    }

}
