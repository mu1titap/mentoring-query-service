package com.example.section.dto.messageIn;

import lombok.*;

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
    private String description;
    private String detail;
    private String mentorUuid;
    private Boolean isReusable;
    private Boolean isDeleted;
    private String thumbnailUrl;

    @Setter
    private List<MentoringCategoryAfterOutDto> categoryList;

}
