package com.example.section.elasticSearch.infrastructure;

import com.example.section.elasticSearch.entity.EsMentoring;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface MentoringElasticRepository extends ElasticsearchRepository<EsMentoring, String> {

}
