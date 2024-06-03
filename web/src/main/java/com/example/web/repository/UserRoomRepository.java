package com.example.web.repository;

import com.example.web.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
    Optional<UserRoom> findByUserIdAndRoomId(Integer userId, Integer roomId);
}
