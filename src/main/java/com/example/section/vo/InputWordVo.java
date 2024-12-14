package com.example.section.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
public class InputWordVo {
    //
    @Schema(description = "검색어", example = "맨토링", nullable = true)
    private String word;

    @Schema(description = "직접 입력 여부 (자동완성으로 검색한거 아니면 true) ", nullable = false)
    private Boolean isDirect;
}
