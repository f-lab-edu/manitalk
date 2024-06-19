package com.example.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketMessageBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public void broadcast(String channel, String message) {
        messagingTemplate.convertAndSend("/" + channel, message);
    }
}
