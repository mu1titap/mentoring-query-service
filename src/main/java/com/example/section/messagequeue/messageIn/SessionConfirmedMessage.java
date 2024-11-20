package com.example.section.messagequeue.messageIn;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SessionConfirmedMessage {
    private String sessionUuid;
    private Boolean sessionIsConfirmed;
}
