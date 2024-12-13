package com.example.section.elasticSearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SuggestedNameResponseDto {
    private List<SuggestedName> suggestedNames;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class SuggestedName {
        private String name;
        private String mentoringId;
    }}
