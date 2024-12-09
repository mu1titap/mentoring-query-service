package com.example.section.entity.vo;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SessionUser {
    private String userUuid;
    private String nickName;
    private String menteeImageUrl;
}
