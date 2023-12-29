package com.example.demo.chatlog;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatlogRepository extends MongoRepository<UserChatlogs, String> {
}
