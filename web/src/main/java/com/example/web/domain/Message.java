package com.example.web.domain;

import com.example.web.enums.MessageType;
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

    private final Integer roomId;

    private final Integer userId;

    private final MessageType type;

    private final String content;

    public Message(Integer roomId, Integer userId, MessageType type, String content) {
        this.roomId = roomId;
        this.userId = userId;
        this.type = type;
        this.content = content;
    }

    @CreatedDate
    private LocalDateTime timestamp;
}
