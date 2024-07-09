package com.example.websocket.event;

import com.example.websocket.vo.MessageVo;
import lombok.Getter;

@Getter
public class MessageEvent {
    private final MessageVo messageVo;

    public MessageEvent(MessageVo messageVo) {
        this.messageVo = messageVo;
    }
}
