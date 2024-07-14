package com.example.web.event;

import com.example.web.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomEvent {
    protected EventType eventType;
    protected Integer roomId;
}
