package com.example.web.service;

public interface MessagePublisher {
    void publish(String channel, Object message);
}
