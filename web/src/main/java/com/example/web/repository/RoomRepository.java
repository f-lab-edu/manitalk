package com.example.web.repository;

import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import com.example.web.repository.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends BaseRepository<Room, Integer> {
    boolean existsByIdAndType(Integer id, RoomType type);
}
