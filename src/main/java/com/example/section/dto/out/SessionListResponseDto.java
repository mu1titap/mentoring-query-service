package com.example.section.dto.out;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SessionListResponseDto {
    private Integer totalCount;
    private LocalDate startDate;

    private List<MentoringSessionResponseDto> mentoringSessionResponseDtoList;
}
