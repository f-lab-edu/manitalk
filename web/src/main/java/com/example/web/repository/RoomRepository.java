package com.example.web.repository;

import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByIdAndType(Integer id, RoomType type);
}
