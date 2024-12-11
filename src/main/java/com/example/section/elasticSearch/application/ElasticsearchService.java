package com.example.section.elasticSearch.application;

import java.util.List;

public interface ElasticsearchService {
    // nori tokenizer 를 사용하여 텍스트 분석한 결과를 반환
    List<String> analyzeText(String text);
    // 검색어 자동완성
    List<String> getSuggestions(String prefix);

}
