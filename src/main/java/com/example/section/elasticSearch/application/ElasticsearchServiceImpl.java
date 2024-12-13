package com.example.section.elasticSearch.application;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.TermSuggestOption;
import com.example.section.elasticSearch.dto.SuggestedNameResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ElasticsearchServiceImpl implements ElasticsearchService{
    private final ElasticsearchClient elasticsearchClient;


    // 자동완성
    @Override
    public SuggestedNameResponseDto getAutocompleteSuggestions(String inputWord) {
        try {
            // Elasticsearch 요청 생성 및 실행
            SearchResponse<Map> response = elasticsearchClient.search(s -> s
                            .index("mentoring_index") // 검색할 인덱스
                            .query(q -> q
                                    .match(m -> m
                                            .field("name") // 검색할 필드
                                            .query(inputWord) // 검색어
                                    )
                            )
                            .size(15), // 최대 결과 수
                    Map.class // "_source"를 Map 형태로 매핑
            );

            log.info("total count: " + response.hits().total().value());
            List<SuggestedNameResponseDto.SuggestedName> suggestedNames = new ArrayList<>(response.hits().hits().stream()
                    .map(hit -> SuggestedNameResponseDto.SuggestedName.builder()
                            .name((String) hit.source().get("name")) // "_source"의 "name" 필드 추출
                            .mentoringId((String) hit.source().get("mentoringId")) // "_source"의 "mentoringId" 필드 추출
                            .build()
                    )
                    .collect(Collectors.toMap(
                            SuggestedNameResponseDto.SuggestedName::getName, // 중복 제거 기준 필드
                            suggestedName -> suggestedName, // 값 그대로 유지
                            (existing, replacement) -> existing // 중복 시 기존 값 유지
                    ))
                    .values());

            return SuggestedNameResponseDto.builder()
                    .suggestedNames(suggestedNames)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed Es getAutocompleteSuggestions", e);
        }
    }

    // 오타교정
    @Override
    public String getSpellingCorrection(String indexName, String fieldName, String text) {
        try {
            // Elasticsearch 요청 생성 및 실행
            SearchResponse<Void> response = elasticsearchClient.search(s -> s
                            .index(indexName) // 검색할 인덱스
                            .suggest(su -> su
                                    .suggesters("spellCorrection", suBuilder -> suBuilder
                                            .text(text) // 검색할 텍스트
                                            .term(t -> t
                                                    .field(fieldName) // 오타 교정 필드
                                                    .maxEdits(2) // 허용할 오타 개수
                                                    .size(1) // 최대 1개 결과
                                            )
                                    )
                            ),
                    Void.class // 검색 응답의 타입 (Void로 설정)
            );

            // 교정 결과 확인 및 처리
            return response.suggest()
                    .get("spellCorrection")
                    .stream()
                    .flatMap(suggestion -> suggestion.term().options().stream()) // Term 옵션 추출
                    .map(TermSuggestOption::text) // 교정된 text 값 추출
                    .findFirst() // 첫 번째 결과만 선택
                    .orElse(""); // 결과가 없으면 공백 반환

        } catch (Exception e) {
            throw new RuntimeException("Elasticsearch 오타 제안 에러 ", e);
        }
    }



}
