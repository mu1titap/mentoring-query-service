package com.example.section.messagequeue.messageIn;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MentoringCategoryAfterOutDto {
    private String topCategoryCode;
    private String middleCategoryCode;
    private String bottomCategoryCode;
    // 카테고리명
    private String topCategoryName;
    private String middleCategoryName;
    private String bottomCategoryName;


}