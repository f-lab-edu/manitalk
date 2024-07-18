package com.example.web.repository.mongo;

public interface MessageCustomRepository {
    Integer aggregateMissionCount(Integer roomId, Integer userId, String mission);
}
