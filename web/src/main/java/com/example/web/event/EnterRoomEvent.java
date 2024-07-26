package com.example.web.event;

import com.example.web.enums.EventType;
import com.example.web.enums.RoomType;
import lombok.Getter;

@Getter
public class EnterRoomEvent extends RoomEvent {
    private final RoomType roomtype;
    private final Integer userId;
    private final String nickname;

    public EnterRoomEvent(Integer roomId, RoomType roomType, Integer userId, String nickname) {
        super(EventType.ENTER_ROOM, roomId);
        this.userId = userId;
        this.roomtype = roomType;
        this.nickname = nickname;
    }
}
