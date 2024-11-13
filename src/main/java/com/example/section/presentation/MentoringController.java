package com.example.section.presentation;

import com.example.section.application.MentoringQueryService;
import com.example.section.common.entity.BaseResponse;
import com.example.section.common.entity.BaseResponseStatus;
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
    private final MentoringQueryService mentoringQueryService;

    @Operation(summary = "멘토링 uuid로 멘토링 단 건 조회" , description = "멘토링 UUID 로 멘토링 조회" ,tags = {"멘토링"})
    @GetMapping("mentoring/{mentoringUuid}")
    public BaseResponse<MentoringResponseDto> getMentoring(@PathVariable("mentoringUuid") String mentoringUuid) {
        return new BaseResponse<>(mentoringQueryService.getMentoringByMentoringUuid(mentoringUuid));
    }

    @Operation(summary = "멘토 uuid로 재사용 등록한 멘토링 리스트 조회" , description = "멘토 UUID 로 재사용 가능한 멘토링 리스트 조회"
            ,tags = {"멘토링"})
    @GetMapping("/reusable-mentoring-list/{mentorUuid}")
    public BaseResponse<List<MentoringReusableResponseDto>> searchReusableMentoringList(
            @PathVariable("mentorUuid") String mentorUuid)
    {
        return new BaseResponse<>(mentoringQueryService.getReusableMentoringListByMentorUuid(mentorUuid));
    }

    @Operation(summary = "멘토uuid로 멘토링 리스트 조회" , description = "멘토 UUID 로 삭제되지 않은 멘토링 리스트 조회"
            ,tags = {"멘토링"} )
    @GetMapping("/mentoring-list/{mentorUuid}")
    public BaseResponse<List<Mentoring>> searchMentoringListByMentorUuid(
            @PathVariable("mentorUuid") String mentorUuid)
    {
        return new BaseResponse<>(mentoringQueryService.findAllByMentorUuidAndIsDeletedFalse(mentorUuid));
    }

    @Operation(summary = "멘토링 세션 리스트 조회" , description = "멘토링 UUID 로 멘토링 세션 리스트 조회" ,tags = {"멘토링 세션"})
    @GetMapping("/session-list/{mentoringUuid}")
    public BaseResponse<List<MentoringSessionResponseDto>> getMentoringSessions(
            @PathVariable("mentoringUuid") String mentoringUuid)
    {
        return new BaseResponse<>(mentoringQueryService.findAllByMentoringUuid(mentoringUuid));
    }

    @Operation(summary = "세션 uuid로 멘토링 세션 단 건 조회" , description = "세션 UUID 로 멘토링 세션 조회" ,tags = {"멘토링 세션"})
    @GetMapping("/session/{sessionUuid}")
    public BaseResponse<MentoringSessionResponseDto> getMentoringSession(@PathVariable("sessionUuid") String sessionUuid) {
        return new BaseResponse<>(mentoringQueryService.findBySessionUuid(sessionUuid));
    }

}
