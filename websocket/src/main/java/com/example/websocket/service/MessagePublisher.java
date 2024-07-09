package com.example.websocket.service;

public interface MessagePublisher {
    void publish(String channel, Object message);
}
