package com.example.web.event;

import com.example.web.enums.EventType;
import lombok.Getter;

@Getter
public class EnterRoomEvent extends RoomEvent {

    private final Integer userId;
    private final String nickname;

    public EnterRoomEvent(Integer roomId, Integer userId, String nickname) {
        super(EventType.ENTER_ROOM, roomId);
        this.userId = userId;
        this.nickname = nickname;
    }
}
