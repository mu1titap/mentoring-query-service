package com.example.section.elasticSearch.application;

import com.example.section.elasticSearch.dto.SuggestedNameResponseDto;
import com.example.section.messagequeue.messageIn.MentoringAddAfterOutDto;
import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;

import java.util.List;

public interface ElasticsearchService {
    // nori tokenizer 를 사용하여 텍스트 분석한 결과를 반환
    // 자동완성
    SuggestedNameResponseDto getAutocompleteSuggestions(String inputWord);

    // 오타교정
    String getSpellingCorrection(String inputWord);

    void createEsMentoring(MentoringAddAfterOutDto mentoringAddAfterOutDto);

    void updateEsMentoring(MentoringEditRequestOutDto mentoringEditRequestOutDto);

}
