package com.example.section.dto.out;

import com.example.section.messagequeue.messageIn.MentoringCategoryAfterOutDto;
import com.example.section.entity.Mentoring;
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
public class MentoringResponseDto {

    private String mentoringUuid;

    private String name;

    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MentoringCategoryResponseDto> categoryList;

    public static MentoringResponseDto fromEntity(Mentoring mentoring) {
        return MentoringResponseDto.builder()
                .mentoringUuid(mentoring.getMentoringUuid())
                .name(mentoring.getName())
                .detail(mentoring.getDetail())
                .mentorUuid(mentoring.getMentorUuid())
                .thumbnailUrl(mentoring.getThumbnailUrl())
                .isReusable(mentoring.getIsReusable())
                .isDeleted(mentoring.getIsDeleted())
                .createdAt(mentoring.getCreatedAt())
                .updatedAt(mentoring.getUpdatedAt())
                .categoryList(from(mentoring.getMentoringCategoryList()))
                .build();
    }

    public static List<MentoringCategoryResponseDto> from(
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
}
