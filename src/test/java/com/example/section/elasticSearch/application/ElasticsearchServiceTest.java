package com.example.section.elasticSearch.application;

import com.example.section.elasticSearch.dto.SuggestedNameResponseDto;
import com.example.section.messagequeue.messageIn.MentoringEditRequestOutDto;
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
        String text = "맨토링asdasdasdasdasdasdasdasdas";

        // When
        String correctedText = elasticsearchService.getSpellingCorrection(text);


        log.info("Corrected Spelling: {}", correctedText);

    }

    @Test
    void updateEsMentoring(){
//        // 125
//        //
//        MentoringEditRequestOutDto test = MentoringEditRequestOutDto.builder().id("125").name("어뎁터스 11년 첨삭 전문가, 자기소개서 첨삭 해드립니다.").build();
//        elasticsearchService.updateEsMentoring(test);
    }



}