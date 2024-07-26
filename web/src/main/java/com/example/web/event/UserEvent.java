package com.example.web.event;

import com.example.web.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEvent {
    protected EventType eventType;
    protected Integer userId;
}
