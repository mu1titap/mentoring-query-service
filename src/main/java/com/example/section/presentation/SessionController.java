package com.example.section.presentation;

import com.example.section.application.SessionService;
import com.example.section.common.entity.BaseResponse;
import com.example.section.dto.out.MentoringSessionResponseDto;
import com.example.section.dto.out.SessionRoomResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoring-query-service")
public class SessionController {
    private final SessionService sessionService;
    @Operation(summary = "멘토링 세션 리스트 조회 (멘토링uuid, 유저uuid)"
            ,description = "조회 조건은 예약마감일 마감 전 까지.<br/>" +
            "유저 uuid는 필수 아님.<br/>" +
            "로그인 시 유저 uuid를 넘겨주면 해당 유저의 세션 참여 여부를 체크."
            ,tags = {"멘토링 세션"})
    @GetMapping("/session-list")
    public BaseResponse<List<MentoringSessionResponseDto>> getMentoringSessions(
            @RequestParam("mentoringUuid") String mentoringUuid ,
            @RequestHeader(value = "userUuid", required = false) String userUuid
    )
    {
        return new BaseResponse<>(sessionService.findByMentoringUuidAndDeadlineDate(mentoringUuid,userUuid));
    }

    @Operation(summary = "세션 uuid로 멘토링 세션 단 건 조회" , description = "세션 UUID 로 멘토링 세션 조회" ,tags = {"멘토링 세션"})
    @GetMapping("/session/{sessionUuid}")
    public BaseResponse<MentoringSessionResponseDto> getMentoringSession(@PathVariable("sessionUuid") String sessionUuid) {
        return new BaseResponse<>(sessionService.findBySessionUuid(sessionUuid));
    }


    /**
     *
     * feing client 용
     */
    @Operation(summary = "세션 uuid로 채팅룸 정보(시간,멘토링 정보) 조회",tags = {"멘토링 세션 feign client 용"})
    @GetMapping("/session-room/{sessionUuid}")
    public BaseResponse<SessionRoomResponseDto> findSessionRoomBySessionUuid(@PathVariable("sessionUuid") String sessionUuid) {
        return new BaseResponse<>(sessionService.findSessionRoomBySessionUuid(sessionUuid));
    }

    @Operation(summary = "세션 uuid 로 멘토 uuid 조회",tags = {"멘토링 세션 feign client 용"})
    @GetMapping("/mentor-info/{sessionUuid}")
    public BaseResponse<String> getMentorUuidBySessionUuid(@PathVariable("sessionUuid") String sessionUuid) {
        return new BaseResponse<>(sessionService.getMentorUuidBySessionUuid(sessionUuid));
    }
}
