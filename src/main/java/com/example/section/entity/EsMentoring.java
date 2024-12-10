package com.example.section.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@Document(indexName = "mentoring_index")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setting( replicas = 0,settingPath = "es-settings.json")
public class EsMentoring {
    @Id // ElasticSearch 문서의 고유 식별자
    private String id;

    @Field(type = FieldType.Text, analyzer = "nori")
    // - 데이터 타입: Text
    // - nori 분석기 사용 (한글 형태소 분석)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

}
