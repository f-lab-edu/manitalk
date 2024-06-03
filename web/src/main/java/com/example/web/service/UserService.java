package com.example.web.service;

import com.example.web.domain.User;
import com.example.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUserId(Integer userId) {
        return userRepository.findById(userId);
    }
}
