package com.example.section.dto.in;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "categoryList")
public class MentoringEditRequestOutDto{

    private String id;
    private String uuid;
    private String name;
    private String detail;
    private String mentorUuid;
    private Boolean isReusable;
    private Boolean isDeleted;
    private String thumbnailUrl;

    @Setter
    private List<MentoringCategoryAfterOutDto> categoryList;

}
