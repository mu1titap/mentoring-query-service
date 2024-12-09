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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Operation(summary = "카테고리 코드로 멘토링 조회" , description = "카테고리 코드로 조회 <br/>" +
            "topCategoryCode 만 필수, middleCategoryCode, bottomCategoryCode 는 선택"
            ,tags = {"멘토링"})
    @GetMapping("mentoring/category")
    public BaseResponse<List<MentoringResponseDto> > findAllByCategoryCodes(
            @RequestParam(name = "topCategoryCode") String topCategoryCode,
            @RequestParam(name = "middleCategoryCode",required = false) String middleCategoryCode,
            @RequestParam(name = "bottomCategoryCode", required = false) String bottomCategoryCode
    ) {
        return new BaseResponse<>(mentoringQueryService.findAllByCategoryCodes(topCategoryCode, middleCategoryCode, bottomCategoryCode));
    }
    @Operation(summary = "카테고리 코드로 멘토링 조회 (페이지네이션)" , description = "카테고리 코드로 조회 <br/>" +
            "topCategoryCode 만 필수, middleCategoryCode, bottomCategoryCode 는 선택 <br/>."+
            "오프셋 베이스 페이지네이션 적용"
            ,tags = {"멘토링"})
    @GetMapping("mentoring-pagination/by-category")
    public BaseResponse<Page<MentoringCoreInfoResponseDto>> searchByCategoryCodesPagination(
            @RequestParam(name = "topCategoryCode") String topCategoryCode,
            @RequestParam(name = "middleCategoryCode",required = false) String middleCategoryCode,
            @RequestParam(name = "bottomCategoryCode", required = false) String bottomCategoryCode,
            @ParameterObject Pageable pageable
    ) {
        return new BaseResponse<>(mentoringQueryService.searchByCategoryCodesPagination(topCategoryCode, middleCategoryCode, bottomCategoryCode,pageable));
    }
    //Page<MentoringCoreInfoResponseDto> searchByCategoryCodesPagination(String topCategoryCode, String middleCategoryCode, String bottomCategoryCode, Pageable pageable)
    @Operation(summary = "멘토 uuid로 재사용 등록한 멘토링 리스트 조회" , description = "멘토 UUID 로 재사용 가능한 멘토링 리스트 조회"
            ,tags = {"멘토링"})
    @GetMapping("/reusable-mentoring-list")
    public BaseResponse<List<MentoringReusableResponseDto>> searchReusableMentoringList(
            @RequestHeader("userUuid") String userUuid)
    {
        return new BaseResponse<>(mentoringQueryService.getReusableMentoringListByMentorUuid(userUuid));
    }

    @Operation(summary = "멘토 uuid로 멘토링 리스트 조회" ,
            description = "멘토 UUID 로 삭제되지 않은 멘토 멘토링 리스트 조회. <br/>"+
                        "멘토면 정렬이 => updateAt 내림차순 정렬. <br/>"+
                        "멘티면 정렬이 => 멘토링 세션 열려있는 것, updateAt 내림차순 정렬. <br/>"
            ,tags = {"멘토링"} )
    @GetMapping("/mentoring-list")
    public BaseResponse<List<MentoringCoreInfoResponseDto>> searchMentoringListByMentorUuid(
            @RequestHeader("userUuid") String userUuid, @RequestParam("isMentor") Boolean isMentor
            )
    {
        return new BaseResponse<>(mentoringQueryService.findAllByMentorUuidAndIsDeletedFalse(userUuid, isMentor));
    }
    @Operation(summary = "멘토 uuid로 멘토링 리스트 조회 (페이지네이션)" ,
            description = "멘토 UUID 로 삭제되지 않은 멘토 멘토링 리스트 조회. <br/>"+
                            "멘토면 정렬이 => updateAt 내림차순 정렬. <br/>"+
                            "멘티면 정렬이 => 멘토링 세션 열려있는 것, updateAt 내림차순 정렬. <br/>"
            ,tags = {"멘토링"} )
    @GetMapping("/mentoring-list-pagination")
    public BaseResponse<Page<MentoringCoreInfoResponseDto>> searchMentoringByMentorUuidPagination(
            @RequestHeader("userUuid") String userUuid,
            @RequestParam("isMentor") Boolean isMentor,
            @ParameterObject Pageable pageable
    )
    {
        return new BaseResponse<>(mentoringQueryService.searchMentoringByMentorUuidPagination(userUuid, isMentor, pageable));
    }
    @Operation(summary = "멘토링 이름으로 멘토링 조회 (페이지네이션)" ,
            description = "V1으로 Elastic Search 아님 <br/>"+
                    "멘토링 세션 열려있는 것 우선정렬, updateAt 내림차순 정렬. <br/>"
            ,tags = {"멘토링"} )
    @GetMapping("/mentoring-list-pagination/{name}")
    public BaseResponse<Page<MentoringCoreInfoResponseDto>> searchByNamePagination(
            @PathVariable("name") String name,
            @ParameterObject Pageable pageable
    )
    {
        return new BaseResponse<>(mentoringQueryService.searchByNamePagination(name, pageable));
    }

    // popularMentoring
    @Operation(summary = "인기 멘토링 조회" ,
            description = "1. topCategoryCodeList 필수 아님.<br/>"+
                    "2. 기본적으로 100개 가져옴.<br/>"+
                    "3. 정렬기준 == 세션 존재하는 것 > 점수(리뷰+판매) 내림차순. 업데이트 시간 내림차순<br/>"
            ,tags = {"멘토링"} )
    @GetMapping("/popular-mentoring-list")
    public BaseResponse<List<MentoringCoreInfoResponseDto>> findPopularMentoringList(
            @ParameterObject @RequestParam(name = "topCategoryCodeList", required = false) List<String> topCategoryCodeList
    )
    {
        return new BaseResponse<>(mentoringQueryService.findPopularMentoringList(topCategoryCodeList));
    }

}
