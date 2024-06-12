package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import com.example.web.repository.RoomRepository;
import com.example.web.dto.CreateRoomParam;
import com.example.web.vo.RoomVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomVo createRoom(CreateRoomParam param) {
        Room room = roomRepository.save(new Room(param.getType()));
        return createRoomVo(room);
    }

    public boolean isExistsRoom(Integer roomId) {
        return roomRepository.existsById(roomId);
    }

    public boolean isGroupRoom(Integer roomId) {
        return roomRepository.existsByIdAndType(roomId, RoomType.G);
    }

    private RoomVo createRoomVo(Room room) {
        return new RoomVo(room.getId(), room.getType());
    }
}
