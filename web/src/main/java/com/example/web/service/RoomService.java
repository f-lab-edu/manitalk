package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.dto.CreateRoomsParam;
import com.example.web.enums.RoomType;
import com.example.web.repository.jpa.RoomRepository;
import com.example.web.dto.CreateRoomParam;
import com.example.web.vo.RoomVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomVo createRoom(CreateRoomParam param) {
        Room room = roomRepository.save(new Room(param.getType()));
        return createRoomVo(room);
    }

    public List<Integer> createRooms(CreateRoomsParam param) {
        List<Room> rooms = makeRoomList(param);
        rooms = roomRepository.saveAll(rooms);

        return rooms.stream()
                .map(Room::getId)
                .collect(Collectors.toList());
    }

    private List<Room> makeRoomList(CreateRoomsParam param) {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < param.getCount(); i++) {
            rooms.add(new Room(param.getType()));
        }
        return rooms;
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

    public void softDeleteById(Integer roomId) {
        roomRepository.findById(roomId).ifPresent(
                room -> {
                    room.setDeleted(true);
                    roomRepository.save(room);
                }
        );
    }

    public void softDeleteAllByIds(List<Integer> ids) {
        List<Room> rooms = roomRepository.findAllByIdIn(ids);
        rooms.forEach(room -> room.setDeleted(true));
        roomRepository.saveAll(rooms);
    }
}
