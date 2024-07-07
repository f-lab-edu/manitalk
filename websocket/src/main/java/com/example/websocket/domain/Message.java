package com.example.websocket.domain;

import com.example.websocket.enums.MessageType;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Getter
public class Message {

    @Id
    private String id;

    private final String requestId;

    private final Integer roomId;

    private final Integer userId;

    private final MessageType type;

    private final String content;

    public Message(String requestId, Integer roomId, Integer userId, MessageType type, String content) {
        this.requestId = requestId;
        this.roomId = roomId;
        this.userId = userId;
        this.type = type;
        this.content = content;
    }

    @CreatedDate
    private LocalDateTime timestamp;
}
