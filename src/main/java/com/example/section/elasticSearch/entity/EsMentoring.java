package com.example.section.elasticSearch.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.suggest.Completion;

@Getter
@Setter
@Document(indexName = "mentoring_index")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setting( replicas = 0)
//@Setting( replicas = 0)
public class EsMentoring {
    @Id // ElasticSearch 문서의 고유 식별자
    private String mentoringId;

    @Field(type = FieldType.Text, analyzer = "my_nori_analyzer") // 커스텀 nori 분석기 지정
    //@Field(type = FieldType.Text)
    private String name;

    @CompletionField(analyzer = "my_nori_analyzer", searchAnalyzer = "my_nori_analyzer") // nori 분석기를 자동완성에 적용
    private Completion suggest;
}
