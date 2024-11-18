package com.example.section.messagequeue.messageIn;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("대기"),
    CONFIRMED("확정"),
    CANCELLED_BY_USER("유저 취소"),
    CANCELLED_BY_MENTOR("멘토 멘토링 삭제 취소"),
    CANCELLED_BY_SYSTEM("최소 인원 미달 취소"),
    END("종료");

    // 상태 값 반환
    private final String description;

    Status(String description) {
        this.description = description;
    }

}

