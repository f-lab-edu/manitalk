package com.example.web.repository.mongo;

import com.example.web.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String>, MessageCustomRepository {
}
