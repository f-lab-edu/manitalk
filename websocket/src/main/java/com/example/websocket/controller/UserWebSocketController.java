package com.example.websocket.controller;

import com.example.websocket.service.RedisMessageSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserWebSocketController {

    private final RedisMessageSubscriber subscriber;

    @SubscribeMapping("/event/{userId}")
    public void handleSubscriptionToUser(@DestinationVariable Integer userId) {
        subscriber.subscribeUserChannel(userId);
    }
}
