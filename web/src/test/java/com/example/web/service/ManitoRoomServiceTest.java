package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.exception.room.DuplicatedRoomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ManitoRoomServiceTest {

    @Mock
    private RoomService roomService;

    @Mock
    private ManitoRoomDetailService manitoRoomDetailService;

    @Mock
    private UserRoomService userRoomService;

    @InjectMocks
    private ManitoRoomService manitoRoomService;

    Integer groupRoomId = 1;
    Long expiresDays = 1L;

    @Test
    @DisplayName("새로운 마니또 채팅방들을 생성합니다.")
    void create_manito_rooms() {

        // given
        when(manitoRoomDetailService.isExistsManitoRoomsInGroup(any())).thenReturn(false);

        int memberSize = 11;
        List<Integer> groupRoomMembers = new ArrayList<>();
        for (int i = 1; i < memberSize + 1; i++) {
            groupRoomMembers.add(i);
        }
        when(userRoomService.getRoomMembers(any())).thenReturn(groupRoomMembers);

        int roomSize = (memberSize / 2) + (memberSize % 2);
        List<Integer> roomIds = new ArrayList<>();
        for (int i = 1; i < roomSize + 1; i++) {
            roomIds.add(i);
        }
        when(roomService.createRooms(any())).thenReturn(roomIds);

        // when
        CreateManitoRoomResponse createManitoRoomResponse = manitoRoomService.createManitoRooms(getCreateManitoRoomsRequest());

        // then
        Assertions.assertEquals(createManitoRoomResponse.getGroupRoomId(), groupRoomId);
        Assertions.assertEquals(createManitoRoomResponse.getManitoRoomCount(), roomSize);
    }

    @Test
    @DisplayName("마니또 채팅방 생성에 실패합니다. - 이미 진행중인 마니또 채팅 존재")
    void create_manito_rooms_이미_진행중인_마니또_채팅_존재() {

        //given
        when(manitoRoomDetailService.isExistsManitoRoomsInGroup(any())).thenReturn(true);

        //when & then
        Assertions.assertThrows(DuplicatedRoomException.class, () -> {
            manitoRoomService.createManitoRooms(getCreateManitoRoomsRequest());
        });
    }

    CreateManitoRoomRequest getCreateManitoRoomsRequest() {
        return new CreateManitoRoomRequest(
                groupRoomId,
                expiresDays
        );
    }
}