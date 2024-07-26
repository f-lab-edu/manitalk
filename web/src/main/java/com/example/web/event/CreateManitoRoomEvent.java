package com.example.web.event;

import com.example.web.enums.EventType;
import lombok.Getter;

@Getter
public class CreateManitoRoomEvent extends UserEvent {
    private final Integer roomId;
    public CreateManitoRoomEvent(Integer userId, Integer roomId) {
        super(EventType.CREATE_MANITO_ROOM, userId);
        this.roomId = roomId;
    }
}
