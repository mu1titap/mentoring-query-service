package com.example.section.messagequeue.messageIn;

import com.example.section.entity.vo.SessionUser;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReRegisterSessionUserMessage {
    private String sessionUuid;

    private String menteeUuid;
    private String menteeImageUrl;

    private LocalDate startDate;

    private Boolean shouldCloseSession;

    public SessionUser toSessionUser() {
        return SessionUser.builder()
                .userUuid(this.menteeUuid)
                .menteeImageUrl(this.menteeImageUrl)
                .build();
    }
}
