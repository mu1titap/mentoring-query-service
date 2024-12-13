package com.example.section.dto.out;

import com.example.section.entity.Mentoring;
import com.example.section.messagequeue.messageIn.AfterHashtag;
import com.example.section.messagequeue.messageIn.MentoringCategoryAfterOutDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainMentoringResponseDto {
    private String mentoringUuid;
    private String name;
    private String description;

    private Long totalReviewCount;
    private Double reviewStarAvg;
    private Long totalSaleCount;


    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MentoringCategoryResponseDto> categoryList;
    private List<MentoringHashTagResponseDto> hashTagList;


    public static MainMentoringResponseDto from(Mentoring mentoring) {
        return mentoring != null ? MainMentoringResponseDto.builder()
                .mentoringUuid(mentoring.getMentoringUuid())
                .name(mentoring.getName())
                .description(mentoring.getDescription()!= null ? mentoring.getDescription() : null)
                .totalReviewCount(mentoring.getTotalReviewCount()!=null?mentoring.getTotalReviewCount():0)
                .reviewStarAvg(mentoring.getReviewStarAvg()!=null?mentoring.getReviewStarAvg():0)
                .totalSaleCount(mentoring.getTotalSaleCount()!=null?mentoring.getTotalSaleCount():0)
                .mentorUuid(mentoring.getMentorUuid())
                .thumbnailUrl(mentoring.getThumbnailUrl())
                .isReusable(mentoring.getIsReusable())
                .isDeleted(mentoring.getIsDeleted())
                .createdAt(mentoring.getCreatedAt())
                .updatedAt(mentoring.getUpdatedAt())
                .categoryList(getCategory(mentoring.getMentoringCategoryList()))
                .hashTagList(getHashTag(mentoring.getMentoringHashTagList()))
                .build() : null;
    }
    public static List<MentoringCategoryResponseDto> getCategory(
            List<MentoringCategoryAfterOutDto> mentoringCategories)
    {
        return mentoringCategories != null ? mentoringCategories.stream()
                .map(categoryResponse -> MentoringCategoryResponseDto.builder()
                        .topCategoryCode(categoryResponse.getTopCategoryCode())
                        .middleCategoryCode(categoryResponse.getMiddleCategoryCode())
                        .bottomCategoryCode(categoryResponse.getBottomCategoryCode())
                        .topCategoryName(categoryResponse.getTopCategoryName())
                        .middleCategoryName(categoryResponse.getMiddleCategoryName())
                        .bottomCategoryName(categoryResponse.getBottomCategoryName())
                        .build())
                .toList() : null;
    }
    public static List<MentoringHashTagResponseDto> getHashTag(
            List<AfterHashtag> mentoringHashTagList)
    {
        return mentoringHashTagList != null ? mentoringHashTagList.stream()
                .map(hashTagResponse -> MentoringHashTagResponseDto.builder()
                        .hashtagId(hashTagResponse.getHashtagId())
                        .hashtagName(hashTagResponse.getHashtagName())
                        .build())
                .toList() : null;
    }

}
