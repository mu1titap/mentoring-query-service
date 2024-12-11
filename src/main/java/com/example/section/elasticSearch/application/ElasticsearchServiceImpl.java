package com.example.section.elasticSearch.application;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ElasticsearchServiceImpl implements ElasticsearchService{
    private final ElasticsearchClient elasticsearchClient;
    private final ChosungAnalyzeService chosungAnalyzeService;
    @Override
    public List<String> analyzeText(String text) {
        try {
            // Elasticsearch _analyze API 호출
            AnalyzeResponse response = elasticsearchClient.indices().analyze(a -> a
                    .index("mentoring_index") // 인덱스 명시
                    .analyzer("my_nori_analyzer") // nori 분석기 사용
                    .text(text)
            );

            // Nori 분석 결과에서 토큰 추출 및 중복 제거
            List<String> tokens = response.tokens().stream()
                    .map(AnalyzeToken::token) // AnalyzeToken 객체에서 토큰 추출
                    .distinct() // 중복 제거
                    .collect(Collectors.toList());
            log.info("tokens: {}", Arrays.toString(tokens.toArray()));
            // 새로운 리스트에 초성 추가
            List<String> chosungTokens = tokens.stream()
                    .filter(token -> chosungAnalyzeService.isKorean(token) && token.length() > 1) // 한글이고 1글자 이상 필터링
                    .map(chosungAnalyzeService::extractChosung) // 초성 추출
                    .toList();

            // 기존 토큰 리스트와 초성 리스트를 합침
            tokens.addAll(chosungTokens);

            return tokens;

        } catch (IOException e) {
            throw new RuntimeException("Failed to analyze text with Elasticsearch", e);
        }
    }

    @Override
    public List<String> getSuggestions(String prefix) {
        //
        try {
            // Elasticsearch 요청 생성 및 실행
            SearchResponse<Void> response = elasticsearchClient.search(s -> s
                            .index("mentoring_index") // 검색할 인덱스
                            .suggest(su -> su
                                    .suggesters("wordCompletionSuggest", suBuilder -> suBuilder
                                            .prefix(prefix) // 검색할 prefix
                                            .completion(c -> c
                                                    .field("suggest") // Suggest 필드
                                                    .size(10) // 최대 10개 결과
                                                    .fuzzy(f -> f.fuzziness("2")) // 허용할 오타 개수
                                            )
                                    )
                            ),
                    Void.class // 검색 응답의 타입 (Void로 설정)
            );

            return response.suggest().get("wordCompletionSuggest").stream()
                    .flatMap(suggestion -> suggestion.completion().options().stream()) // Completion 옵션 추출
                    .map(CompletionSuggestOption::text) // 제안된 text 값 추출
                    .filter(suggestion -> suggestion.length() > prefix.length()) // prefix 길이가 2인 경우 필터링

                    .distinct() // 중복 제거
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch suggestions from Elasticsearch", e);
        }
    }
}
