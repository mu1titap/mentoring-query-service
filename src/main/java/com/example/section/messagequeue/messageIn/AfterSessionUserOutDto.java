package com.example.section.messagequeue.messageIn;

import com.example.section.entity.vo.SessionUser;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AfterSessionUserOutDto {
    private String id; // 세션유저 Id
    private String sessionUuid;
    private String menteeUuid;
    private String nickName;
    private String menteeImageUrl;

    private Status status;

    private String mentoringName;
    private Boolean isClosed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SessionUser toSessionUserValueObject() {
        return SessionUser.builder()
                .userUuid(this.menteeUuid)
                .nickName(this.nickName)
                .menteeImageUrl(this.menteeImageUrl)
                .build();
    }

}
