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
    void mentoringReadDataSynchronization(){
        List<Mentoring> all = mentoringMongoRepository.findAll();
        log.info("all size : "+all.size());
        //List<String> analysisResult = elasticsearchService.analyzeText(mentoring.getName());
        all.stream()
                .map(Mentoring::toEsEntity)
                .forEach(mentoringElasticRepository::save);
    }
}