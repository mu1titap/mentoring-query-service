package com.example.section.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentoringHashTagResponseDto {
    private String hashtagId;
    private String hashtagName;
}