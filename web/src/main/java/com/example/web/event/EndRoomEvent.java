package com.example.web.event;

import com.example.web.enums.EventType;
import lombok.Getter;

@Getter
public class EndRoomEvent extends RoomEvent {
    public EndRoomEvent(Integer roomId) {
        super(EventType.END_ROOM, roomId);
    }
}
