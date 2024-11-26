package com.example.section.presentation;

import com.example.section.application.MentoringService;
import com.example.section.application.SessionService;
import com.example.section.common.entity.BaseResponse;
import com.example.section.dto.out.MentoringCoreInfoResponseDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.entity.Mentoring;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoring-query-service")
public class MentoringController {
    private final MentoringService mentoringQueryService;


    @Operation(summary = "멘토링 uuid로 멘토링 단 건 조회" , description = "멘토링 UUID 로 멘토링 조회" ,tags = {"멘토링"})
    @GetMapping("mentoring/{mentoringUuid}")
    public BaseResponse<MentoringResponseDto> getMentoring(@PathVariable("mentoringUuid") String mentoringUuid) {
        return new BaseResponse<>(mentoringQueryService.getMentoringByMentoringUuid(mentoringUuid));
    }

    @Operation(summary = "멘토링 카테고리 코드로 조회" , description = "카테고리 코드로 조회 <br/>" +
            "topCategoryCode 만 필수, middleCategoryCode, bottomCategoryCode 는 선택"
            ,tags = {"멘토링"})
    @GetMapping("mentoring/by-category")
    public BaseResponse<List<MentoringResponseDto> > findAllByCategoryCodes(
            @RequestParam(name = "topCategoryCode") String topCategoryCode,
            @RequestParam(name = "middleCategoryCode",required = false) String middleCategoryCode,
            @RequestParam(name = "bottomCategoryCode", required = false) String bottomCategoryCode
    ) {
        return new BaseResponse<>(mentoringQueryService.findAllByCategoryCodes(topCategoryCode, middleCategoryCode, bottomCategoryCode));
    }
    @Operation(summary = "멘토 uuid로 재사용 등록한 멘토링 리스트 조회" , description = "멘토 UUID 로 재사용 가능한 멘토링 리스트 조회"
            ,tags = {"멘토링"})
    @GetMapping("/reusable-mentoring-list/{mentorUuid}")
    public BaseResponse<List<MentoringReusableResponseDto>> searchReusableMentoringList(
            @RequestHeader("userUuid") String userUuid)
    {
        return new BaseResponse<>(mentoringQueryService.getReusableMentoringListByMentorUuid(userUuid));
    }

    @Operation(summary = "멘토uuid로 멘토링 리스트 조회" , description = "멘토 UUID 로 삭제되지 않은 멘토링 리스트 조회"
            ,tags = {"멘토링"} )
    @GetMapping("/mentoring-list/{mentorUuid}")
    public BaseResponse<List<MentoringCoreInfoResponseDto>> searchMentoringListByMentorUuid(
            @RequestHeader("userUuid") String userUuid)
    {
        return new BaseResponse<>(mentoringQueryService.findAllByMentorUuidAndIsDeletedFalse(userUuid));
    }



}
