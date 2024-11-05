package com.example.section.dto.out;

import com.example.section.entity.Mentoring;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentoringReusableResponseDto {

    private String mentoringUuid;

    private String name;

    private String detail;

    private String thumbnailUrl;


    public static MentoringReusableResponseDto toReusableDto(Mentoring mentoring) {
        return MentoringReusableResponseDto.builder()
                .mentoringUuid(mentoring.getMentoringUuid())
                .name(mentoring.getName())
                .detail(mentoring.getDetail())
                .thumbnailUrl(mentoring.getThumbnailUrl())
                .build();
    }
}
