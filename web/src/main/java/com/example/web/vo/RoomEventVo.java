package com.example.web.vo;

import com.example.web.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class RoomEventVo {
    protected EventType eventType;
    protected Integer roomId;
}
