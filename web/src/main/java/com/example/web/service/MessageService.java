package com.example.web.service;

import com.example.web.repository.mongo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Integer aggregateMissionCount(Integer roomId, Integer userId, String mission) {
        return messageRepository.aggregateMissionCount(roomId, userId, mission);
    }
}

