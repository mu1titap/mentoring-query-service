package com.example.section.dto.out;

import com.example.section.entity.MentoringSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionRoomResponseDto {
    private String mentoringUuid;
    private String mentoringName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public static SessionRoomResponseDto from(MentoringSession mentoringSession) {
        return SessionRoomResponseDto.builder()
                .mentoringUuid(mentoringSession.getMentoringUuid())
                .mentoringName(mentoringSession.getMentoringName())
                .startDate(mentoringSession.getStartDate())
                .endDate(mentoringSession.getEndDate())
                .startTime(mentoringSession.getStartTime())
                .endTime(mentoringSession.getEndTime())
                .build();
    }

}
