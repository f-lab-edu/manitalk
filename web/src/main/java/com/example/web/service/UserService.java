package com.example.web.service;

import com.example.web.domain.User;
import com.example.web.dto.CreateUserParam;
import com.example.web.repository.jpa.UserRepository;
import com.example.web.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isExistsUser(Integer userId) {
        return userRepository.existsById(userId);
    }

    public UserVo getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(value -> new UserVo(
                value.getId(),
                value.getEmail()
        )).orElse(null);
    }

    public UserVo createUser(CreateUserParam param) {
        User user = userRepository.save(new User(param.getEmail()));
        return new UserVo(
                user.getId(),
                user.getEmail()
        );
    }
}
