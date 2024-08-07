package com.example.web.repository.jpa;

import com.example.web.domain.User;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
