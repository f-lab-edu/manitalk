package com.example.websocket.controller;

import com.example.websocket.constant.ChatConstant;
import com.example.websocket.request.SendMessageRequest;
import com.example.websocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(ChatConstant.SOCKET_SERVER_PATH_PREFIX)
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/send")
    public void sendMessage(@Payload SendMessageRequest dto) {
        messageService.sendMessage(dto);
    }
}
