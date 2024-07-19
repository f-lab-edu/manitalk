package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedRoomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

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

    @Mock
    private ManitoMissionService manitoMissionService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private ManitoRoomService manitoRoomService;

    Integer groupRoomId = 1;
    Long expiresDays = 1L;
    Integer userId = 1;
    Integer roomId = 1;
    String nickname = "test";
    Integer userRoomId = 1;

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
        when(userRoomService.getUserIdsByRoomId(any())).thenReturn(groupRoomMembers);

        int roomSize = (memberSize / 2) + (memberSize % 2);
        List<Integer> roomIds = new ArrayList<>();
        for (int i = 1; i < roomSize + 1; i++) {
            roomIds.add(i);
        }
        when(roomService.createRooms(any())).thenReturn(roomIds);

        List<Integer> userRoomIds = new ArrayList<>();
        for (int i = 1; i < roomSize*2 + 1; i++) {
            userRoomIds.add(i);
        }
        when(userRoomService.createUserRooms(any())).thenReturn(userRoomIds);

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

    @Test
    @DisplayName("마니또 채팅방에 입장합니다.")
    void enter_manito_room() {

        // given
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(true);
        when(userRoomService.getUserRoomId(any(), any())).thenReturn(userRoomId);

        // when
        EnterManitoRoomResponse enterManitoRoomResponse = manitoRoomService.enterManitoRoom(getEnterManitoRoomsRequest());

        // then
        Assertions.assertEquals(enterManitoRoomResponse.getUserRoomId(), userRoomId);
    }

    @Test
    @DisplayName("마니또 채팅방 입장에 실패합니다. - 채팅방의 멤버가 아님")
    void enter_manito_room_채팅방의_멤버가_아님() {

        //given
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(CanNotEnterRoomException.class, () -> {
            manitoRoomService.enterManitoRoom(getEnterManitoRoomsRequest());
        });
    }

    EnterManitoRoomRequest getEnterManitoRoomsRequest() {
        return new EnterManitoRoomRequest(userId,roomId,nickname);
    }
}