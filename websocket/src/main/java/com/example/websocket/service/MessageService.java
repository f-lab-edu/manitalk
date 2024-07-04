package com.example.websocket.service;

import com.example.websocket.domain.Message;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.vo.MessageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import request.SendMessageRequest;

@Controller
@RequiredArgsConstructor
public class MessageService {

    @Value("${room.channel.prefix}")
    String channelPrefix;

    private final MessagePublisher messagePublisher;

    private final MessageRepository messageRepository;

    @MessageMapping("/send")
    @Transactional
    public void sendMessage(@Payload SendMessageRequest dto) {
        MessageVo messageVo = saveMessage(dto);
        publishMessage(messageVo);
    }

    private MessageVo saveMessage(SendMessageRequest dto) {
        Message message = new Message(
                dto.getRoomId(),
                dto.getUserId(),
                dto.getMessageType(),
                dto.getContent()
        );
        message = messageRepository.save(message);

        return new MessageVo(
                message.getId(),
                message.getRoomId(),
                message.getUserId(),
                message.getType(),
                message.getContent()
        );
    }

    private void publishMessage(MessageVo messageVo) {
        messagePublisher.publish(
                channelPrefix + "/" + messageVo.getRoomId(),
                messageVo
        );
    }
}
