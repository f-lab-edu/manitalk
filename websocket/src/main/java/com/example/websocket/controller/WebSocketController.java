package com.example.websocket.controller;

import com.example.websocket.service.RedisMessageSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final RedisMessageSubscriber subscriber;

    @SubscribeMapping("/{roomId}")
    public void handleSubscription(@DestinationVariable Integer roomId) {
        subscriber.subscribeRoomChannel(roomId);
    }
}
