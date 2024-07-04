package com.example.websocket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    T("Text"),
    F("File");

    private final String description;
}
