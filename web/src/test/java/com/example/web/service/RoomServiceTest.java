package com.example.web.service;

import com.example.web.dto.CreateRoomsParam;
import com.example.web.enums.RoomType;
import com.example.web.dto.CreateRoomParam;
import com.example.web.repository.fake.FakeRoomRepository;
import com.example.web.vo.RoomVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    private RoomService roomService;

    @BeforeEach
    public void setUp() throws Exception {
        roomService = new RoomService(new FakeRoomRepository());
    }

    @Test
    @DisplayName("새로운 채팅방을 생성합니다.")
    void create_room() {

        // given
        RoomType roomType = RoomType.G;
        CreateRoomParam createRoomParam = CreateRoomParam.builder().type(roomType).build();

        // when
        RoomVo roomVo = roomService.createRoom(createRoomParam);

        // then
        Assertions.assertEquals(roomVo.getRoomType(), roomType);
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

        // when
        List<Integer> roomIds = roomService.createRooms(createRoomsParam);

        // then
        Assertions.assertEquals(roomIds.size(), count);
    }
}