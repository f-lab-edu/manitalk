package com.example.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    MESSAGE("메시지 발신"),
    END_ROOM("채팅 종료");

    private final String description;
}