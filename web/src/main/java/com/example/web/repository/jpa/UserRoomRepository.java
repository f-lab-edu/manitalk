package com.example.web.repository.jpa;

import com.example.web.domain.UserRoom;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoomRepository extends BaseRepository<UserRoom, Integer> {

    boolean existsByUserIdAndRoomId(Integer userId, Integer roomId);

    @Query("SELECT ur.user.id FROM UserRoom ur where ur.room.id = :roomId")
    List<Integer> findUserIdsByRoomId(@Param("roomId") Integer roomId);

    List<UserRoom> findByRoomId(Integer roomId);

    @Query("SELECT ur.id FROM UserRoom ur where ur.user.id = :userId AND ur.room.id = :roomId")
    Integer findIdByUserIdAndRoomId(Integer userId, Integer roomId);

    List<UserRoom> findAllByRoomIdIn(List<Integer> roomIds);
}
