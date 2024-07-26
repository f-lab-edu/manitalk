package com.example.web.repository.jpa;

import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends BaseRepository<Room, Integer> {
    boolean existsByIdAndType(Integer id, RoomType type);

    List<Room> findAllByIdIn(List<Integer> ids);
}
