package com.example.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final WebSocketMessageBroadcaster messageBroadcaster;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageBody = new String(message.getBody());
        String channel = new String(message.getChannel());

        // TODO: 로깅 작업을 추가합니다.
        System.out.println("channel: " + channel + " message: " + messageBody);

        messageBroadcaster.broadcast(channel, messageBody);
    }
}
