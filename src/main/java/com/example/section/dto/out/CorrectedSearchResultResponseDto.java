package com.example.section.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CorrectedSearchResultResponseDto {

    private String spellingCorrection;
    private Page<MentoringCoreInfoResponseDto> searchResults;
}
