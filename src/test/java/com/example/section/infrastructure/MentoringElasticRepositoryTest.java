package com.example.section.infrastructure;

import com.example.section.elasticSearch.application.ElasticsearchService;
import com.example.section.elasticSearch.entity.EsMentoring;
import com.example.section.elasticSearch.infrastructure.MentoringElasticRepository;
import com.example.section.entity.Mentoring;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Log4j2
class MentoringElasticRepositoryTest {
    @Autowired
    private MentoringElasticRepository mentoringElasticRepository;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private MentoringMongoRepository mentoringMongoRepository;

    @Test
    void createEsMentoring() {
        String name = "고전게임 포트리스 멘토링";
        List<String> strings = elasticsearchService.analyzeText(name);
        Completion completion = new Completion(strings.toArray(new String[0]));
        EsMentoring esMentoring = EsMentoring.builder()
                .name(name)
                .suggest(completion) // 수동으로 Suggest 값 설정
                .build();
        mentoringElasticRepository.save(esMentoring);
    }

    @Test
    void analyzeEsMentoring() {
        String name = "포트나이트";
        List<String> strings = elasticsearchService.analyzeText(name);
        log.info(Arrays.toString(strings.toArray()));
    }

    @Test
    void getSuggestions(){
        List<String> result = elasticsearchService.getSuggestions("포르트");
        log.info(Arrays.toString(new List[]{result}));

    }

//    @Test
//    void mentoringReadDataSynchronization(){
//        List<Mentoring> all = mentoringMongoRepository.findAll();
//        log.info("all size : "+all.size());
//        all.stream()
//                .map(mentoring -> {
//                    List<String> analysisResult = elasticsearchService.analyzeText(mentoring.getName());
//                    return mentoring.toEsEntity(mentoring, analysisResult);
//                })
//                .forEach(mentoringElasticRepository::save);
//    }
}