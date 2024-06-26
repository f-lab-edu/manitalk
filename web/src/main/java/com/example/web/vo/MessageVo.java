package com.example.web.vo;

import com.example.web.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MessageVo {
    private String id;

    private Integer roomId;

    private Integer userId;

    private MessageType type;

    private String content;
}
