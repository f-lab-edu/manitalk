package com.example.web.vo;

import com.example.web.enums.EventType;
import com.example.web.enums.MessageType;
import lombok.Getter;

@Getter
public class MessageVo extends RoomEventVo {
    private final String id;

    private final Integer userId;

    private final MessageType type;

    private final String content;

    public MessageVo(String id, Integer roomId, Integer userId, MessageType type, String content) {
        super(EventType.MESSAGE, roomId);
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.content = content;
    }
}
