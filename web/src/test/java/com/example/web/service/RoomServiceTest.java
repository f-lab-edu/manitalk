package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.dto.CreateRoomsParam;
import com.example.web.enums.RoomType;
import com.example.web.repository.RoomRepository;
import com.example.web.dto.CreateRoomParam;
import com.example.web.vo.RoomVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    Integer roomId = 1;

    Room room;

    @BeforeEach
    public void setUp() throws Exception {
        room = new Room();
        setId(room, roomId);
    }

    @Test
    @DisplayName("새로운 채팅방을 생성합니다.")
    void create_room() {

        // given
        CreateRoomParam createRoomParam = CreateRoomParam.builder().type(RoomType.G).build();
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // when
        RoomVo roomVo = roomService.createRoom(createRoomParam);

        // then
        Assertions.assertEquals(roomVo.getId(), roomId);
    }

    @Test
    @DisplayName("채팅방 여러개를 한번에 생성합니다.")
    void create_rooms() {

        // given
        int count = 4;

        CreateRoomsParam createRoomsParam = CreateRoomsParam.builder()
                .type(RoomType.M)
                .count(count)
                .build();

        List<Room> rooms = new ArrayList<>();
        for (int i=0; i < count; i++) {
            Room room = new Room(RoomType.M);
            rooms.add(room);
        }
        when(roomRepository.saveAll(any())).thenReturn(rooms);

        // when
        List<Integer> roomIds = roomService.createRooms(createRoomsParam);

        // then
        Assertions.assertEquals(roomIds.size(), count);
    }

    private void setId(Room room, Integer id) throws Exception {
        Field idField = Room.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(room, id);
    }
}