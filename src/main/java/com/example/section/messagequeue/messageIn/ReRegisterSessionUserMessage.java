package com.example.section.messagequeue.messageIn;

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

    private LocalDate startDate;

    private Boolean shouldCloseSession;
}
