package com.example.web.repository;

import com.example.web.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    boolean existsByUserIdAndRoomId(Integer userId, Integer roomId);

    @Query("SELECT ur.user.id FROM UserRoom ur where ur.room.id = :roomId")
    List<Integer> findUserIdByRoomId(@Param("roomId") Integer roomId);
}
