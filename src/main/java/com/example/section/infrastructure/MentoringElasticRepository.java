package com.example.section.infrastructure;

import com.example.section.entity.EsMentoring;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

@Repository

public interface MentoringElasticRepository extends ElasticsearchRepository<EsMentoring, String> {
}
