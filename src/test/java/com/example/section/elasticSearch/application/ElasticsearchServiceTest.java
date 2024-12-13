package com.example.section.elasticSearch.application;

import com.example.section.elasticSearch.dto.SuggestedNameResponseDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.Normalizer;
import java.util.List;


@SpringBootTest
@Log4j2
class ElasticsearchServiceTest {
    @Autowired
    ElasticsearchService elasticsearchService;


    @Test
    void testAutocompleteSuggestions() {
        // Given
        String indexName = "mentoring_index";
        String fieldName = "name";
        String prefix = "포트";

        // When
        SuggestedNameResponseDto autocompleteSuggestions = elasticsearchService.getAutocompleteSuggestions(prefix);

        // Then
    }



    @Test
    void testSpellingCorrection() {
        // Given
        String indexName = "mentoring_index";
        String fieldName = "name";
        String text = "포트";

        // When
        String correctedText = elasticsearchService.getSpellingCorrection(indexName, fieldName, text);


        log.info("Corrected Spelling: {}", correctedText);
        //String result = Normalizer.normalize(correctedText, Normalizer.Form.NFC);

        String combine = HangulUtils.combine(correctedText);
        log.info("result: {}", combine);

    }

    @Test
    void asd(){

    }



}