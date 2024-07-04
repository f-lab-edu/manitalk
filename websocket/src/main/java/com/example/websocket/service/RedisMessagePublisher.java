package com.example.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.channel.pattern}")
    String channelPattern;

    @Override
    public void publish(String channel, Object message) {
        System.out.println(channelPattern + channel);
        redisTemplate.convertAndSend(channelPattern + channel, message);
    }
}
