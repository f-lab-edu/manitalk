package com.example.web.repository;

import com.example.web.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
    boolean existsByUserIdAndRoomId(Integer userId, Integer roomId);
}
