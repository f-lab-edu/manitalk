package com.example.web.repository;

import com.example.web.domain.FailedRoomEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FailedEventRepository extends MongoRepository<FailedRoomEvent, String> {
}
