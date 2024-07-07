package com.example.websocket.vo;

import com.example.websocket.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MessageVo {
    private String id;

    private String requestId;

    private Integer roomId;

    private Integer userId;

    private MessageType type;

    private String content;
}
