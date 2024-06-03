package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> findByRoomId(Integer roomId) {
        return roomRepository.findById(roomId);
    }
}
