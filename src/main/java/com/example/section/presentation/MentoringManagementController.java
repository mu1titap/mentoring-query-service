package com.example.section.presentation;

import com.example.section.application.MentoringManagementService;
import com.example.section.application.MentoringService;
import com.example.section.common.entity.BaseResponse;
import com.example.section.common.entity.BaseResponseStatus;
import com.example.section.dto.out.MainMentoringResponseDto;
import com.example.section.dto.out.MentoringCoreInfoResponseDto;
import com.example.section.dto.out.MentoringResponseDto;
import com.example.section.dto.out.MentoringReusableResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "멘토링 관리", description = "멘토링 관리 API")
public class MentoringManagementController {
    private final MentoringManagementService mentoringManagementService;

    @Operation(summary = "멘토링 uuid로 메인 멘토링 설정", description = "멘토링 UUID로 메인 멘토링 설정", tags = {"멘토링"})
    @PutMapping("main/set")
    public BaseResponse<Void> setMainMentoring(@RequestParam("mentoringUuid") String mentoringUuid) {
        mentoringManagementService.setMainMentoring(mentoringUuid);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "멘토링 uuid로 메인 멘토링 해제", description = "멘토링 UUID로 메인 멘토링 해제", tags = {"멘토링"})
    @PutMapping("main/unset")
    public BaseResponse<Void> unSetMainMentoring(@RequestParam("mentoringUuid") String mentoringUuid) {
        mentoringManagementService.unSetMainMentoring(mentoringUuid);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "메인 멘토링 리스트 조회(최대 10개)", description = "메인 멘토링 리스트 조회 <br/>" +
            "- 업데이트 시간 내림차순으로 정렬<br/>" +
            "- 최대 10개만 조회", tags = {"멘토링"})
    @GetMapping("main/list")
    public BaseResponse<List<MainMentoringResponseDto>> getMainMentoringList() {
        return new BaseResponse<>(mentoringManagementService.getMainMentoringList());
    }

}
