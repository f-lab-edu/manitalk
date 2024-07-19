package com.example.web.repository.jpa;

import com.example.web.domain.UserRoom;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRepository extends BaseRepository<UserRoom, Integer> {

    boolean existsByUserIdAndRoomId(Integer userId, Integer roomId);

    @Query("SELECT ur.user.id FROM UserRoom ur where ur.room.id = :roomId")
    List<Integer> findUserIdsByRoomId(@Param("roomId") Integer roomId);

    void deleteByRoomId(Integer roomId);

    List<UserRoom> findByRoomId(Integer roomId);

    Optional<UserRoom> findByUserIdAndRoomId(Integer userId, Integer roomId);
}
