package com.example.web.service;

import com.example.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isExistsUser(Integer userId) {
        return userRepository.existsById(userId);
    }
}
