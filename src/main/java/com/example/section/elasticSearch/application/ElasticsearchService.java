package com.example.section.elasticSearch.application;

import java.util.List;

public interface ElasticsearchService {
    List<String> analyzeText(String text);

    List<String> getSuggestions(String prefix);

}
