package com.example.websocket.controller;

import com.example.websocket.request.SendMessageRequest;
import com.example.websocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller()
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/send")
    public void sendMessage(@Payload SendMessageRequest dto) {
        messageService.sendMessage(dto);
    }
}
