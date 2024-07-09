package com.example.websocket.service;

import com.example.websocket.domain.Message;
import com.example.websocket.event.MessageEvent;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.vo.MessageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import com.example.websocket.request.SendMessageRequest;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class MessageService {

    private final ApplicationEventPublisher eventPublisher;
    private final MessageRepository messageRepository;

    @Transactional
    public void sendMessage(SendMessageRequest dto) {
        MessageVo messageVo = saveMessage(dto);
        eventPublisher.publishEvent(new MessageEvent(messageVo));
    }

    private MessageVo saveMessage(SendMessageRequest dto) {
        Message message = new Message(
                dto.getRequestId(),
                dto.getRoomId(),
                dto.getUserId(),
                dto.getMessageType(),
                dto.getContent()
        );
        message = messageRepository.save(message);

        return new MessageVo(
                message.getId(),
                message.getRequestId(),
                message.getRoomId(),
                message.getUserId(),
                message.getType(),
                message.getContent()
        );
    }

    @Transactional
    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}
