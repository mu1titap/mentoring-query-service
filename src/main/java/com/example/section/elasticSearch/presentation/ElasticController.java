package com.example.section.elasticSearch.presentation;

import com.example.section.common.entity.BaseResponse;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.elasticSearch.application.ElasticsearchService;
import com.example.section.elasticSearch.dto.SuggestedNameResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoring-query-service")
public class ElasticController {
    private final ElasticsearchService elasticsearchService;

    @Operation(summary = "추천 검색어 제안" , description = "멘토링 UUID 로 멘토링 조회" ,tags = {"검색"})
    @GetMapping("elastic/{inputWord}")
    public BaseResponse<SuggestedNameResponseDto> getAutocompleteSuggestions(@PathVariable("inputWord") String inputWord) {
        return new BaseResponse<>(elasticsearchService.getAutocompleteSuggestions(inputWord));
    }


}
