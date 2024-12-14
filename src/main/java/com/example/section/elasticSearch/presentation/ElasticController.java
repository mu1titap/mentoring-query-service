package com.example.section.elasticSearch.presentation;

import com.example.section.application.MentoringService;
import com.example.section.common.entity.BaseResponse;
import com.example.section.dto.out.CorrectedSearchResultResponseDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.elasticSearch.application.ElasticsearchService;
import com.example.section.elasticSearch.dto.SuggestedNameResponseDto;
import com.example.section.vo.InputWordVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
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
    private final MentoringService MentoringService;

    @Operation(summary = "검색어 자동완성" , description = "1. 최대 15개의 자동완성을 추천받음 <br>"+ "2. 똑같이 중복되는 멘토링 이름이 있다면 중복제거해서 같은 단어나 문장을 추천하지 않음"
            ,tags = {"검색 지원"})
    @GetMapping("elastic/{inputWord}")
    public BaseResponse<SuggestedNameResponseDto> getAutocompleteSuggestions(@PathVariable("inputWord") String inputWord) {
        return new BaseResponse<>(elasticsearchService.getAutocompleteSuggestions(inputWord));
    }


    @Operation(summary = "멘토링 이름으로 멘토링 조회 (페이지네이션+elasticsearch)" ,
            description =
                    "멘토링 세션 열려있는 것 우선정렬, updateAt 내림차순 정렬. <br/>"+
                            "오타 입력한 경우 교정된 단어로 검색하며, 교정된 검색어도 return 해줌 <br/>"
            ,tags = {"검색 지원"} )
    @GetMapping("/mentoring-list-pagination/elasticsearch/{name}")
    public BaseResponse<CorrectedSearchResultResponseDto> elasticSearchByNamePagination(
            @ParameterObject InputWordVo requestVo,
            @ParameterObject Pageable pageable
    )
    {
        String inputWord = requestVo.getWord() != null ? requestVo.getWord() : "";
        return new BaseResponse<>(MentoringService.elasticSearchByNamePagination(inputWord, pageable));
    }


}
