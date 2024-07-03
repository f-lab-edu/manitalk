package com.example.web.vo;

import com.example.web.enums.EventType;
import lombok.Getter;

@Getter
public class EndRoomEventVo extends RoomEventVo {
    public EndRoomEventVo(Integer roomId) {
        super(EventType.END_ROOM, roomId);
    }
}
