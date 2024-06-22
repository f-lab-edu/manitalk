package com.example.web.repository;

import com.example.web.domain.User;
import com.example.web.repository.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
}
