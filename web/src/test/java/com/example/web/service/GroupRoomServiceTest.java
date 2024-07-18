package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.dto.CreateRoomParam;
import com.example.web.dto.CreateUserRoomParam;
import com.example.web.exception.room.CanNotDeleteRoomException;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedUserRoomException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
import com.example.web.vo.GroupRoomDetailVo;
import com.example.web.vo.RoomVo;
import com.example.web.vo.UserRoomVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.web.dto.CreateGroupRoomDetailParam;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupRoomServiceTest {

    @Mock
    private RoomService roomService;

    @Mock
    private GroupRoomDetailService groupRoomDetailService;

    @Mock
    private UserService userService;

    @Mock
    private UserRoomService userRoomService;

    @Mock
    private ManitoRoomDetailService manitoRoomDetailService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private GroupRoomService groupRoomService;

    Integer userId = 1;

    Integer roomId = 1;

    Integer userRoomId = 1;

    Integer roomOwnerId = 1;

    String roomName = "test_room";

    String enterCode = "test_code";

    String nickname = "test";

    EnterGroupRoomRequest enterGroupRoomRequest;

    @BeforeEach
    public void setUp() throws Exception {
        enterGroupRoomRequest = new EnterGroupRoomRequest(
                userId,
                roomId,
                roomName,
                enterCode,
                nickname
        );
    }

    @Test
    @DisplayName("새로운 그룹 채팅방을 생성합니다.")
    void create_group_room() {

        // given
        RoomVo roomVo = new RoomVo(roomId, RoomType.G);
        when(roomService.createRoom(any(CreateRoomParam.class))).thenReturn(roomVo);

        GroupRoomDetailVo groupRoomDetailVo = new GroupRoomDetailVo(roomName, roomOwnerId, enterCode);
        when(groupRoomDetailService.createGroupRoomDetail(any(CreateGroupRoomDetailParam.class)))
                .thenReturn(groupRoomDetailVo);

        CreateGroupRoomRequest dto = new CreateGroupRoomRequest(roomOwnerId, roomName, enterCode);

        // when
        CreateGroupRoomResponse createGroupRoomResponse = groupRoomService.createGroupRoom(dto);

        // then
        Assertions.assertEquals(createGroupRoomResponse.getId(), roomId);
        Assertions.assertEquals(createGroupRoomResponse.getRoomType(), RoomType.G);
        Assertions.assertEquals(createGroupRoomResponse.getRoomOwnerId(), roomOwnerId);
        Assertions.assertEquals(createGroupRoomResponse.getRoomName(), roomName);
        Assertions.assertEquals(createGroupRoomResponse.getEnterCode(), enterCode);
    }

    @Test
    @DisplayName("그룹 채팅방에 입장합니다.")
    void enter_group_room() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(true);
        when(groupRoomDetailService.isRightEnterCode(any(), any())).thenReturn(true);
        when(userService.isExistsUser(any())).thenReturn(true);
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(false);

        UserRoomVo userRoomVo = new UserRoomVo(userRoomId, userId, roomId, nickname);
        when(userRoomService.createUserRoom(any(CreateUserRoomParam.class))).thenReturn(userRoomVo);

        //when
        EnterRoomResponse enterRoomResponse = groupRoomService.enterGroupRoom(enterGroupRoomRequest);

        //then
        Assertions.assertEquals(enterRoomResponse.getUserRoomId(), userRoomId);
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 채팅방 존재하지 않음")
    void enter_group_room_채팅방_존재하지_않음() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomRequest);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 그룹 채팅방 아님")
    void enter_group_room_그룹_채팅방_아님() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomRequest);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 입장 코드 틀림")
    void enter_group_room_입장_코드_틀림() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(true);
        when(groupRoomDetailService.isRightEnterCode(any(), any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(CanNotEnterRoomException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomRequest);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 사용자를 찾을 수 없음")
    void enter_group_room_사용자를_찾을_수_없음() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(true);
        when(groupRoomDetailService.isRightEnterCode(any(), any())).thenReturn(true);
        when(userService.isExistsUser(any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomRequest);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 이미 입장한 사용자")
    void enter_group_room_이미_입장한_사용자() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(true);
        when(groupRoomDetailService.isRightEnterCode(any(), any())).thenReturn(true);
        when(userService.isExistsUser(any())).thenReturn(true);
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(true);

        //when & then
        Assertions.assertThrows(DuplicatedUserRoomException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomRequest);
        });
    }

    @Test
    @DisplayName("그룹 채팅을 종료합니다.")
    void end_group_room() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(true);
        when(groupRoomDetailService.isRoomOwner(any(), any())).thenReturn(true);
        when(manitoRoomDetailService.isExistsManitoRoomsInGroup(any())).thenReturn(false);

        //when
        EndRoomResponse endRoomResponse = groupRoomService.endGroupRoom(createEndGroupRoomRequest());

        //then
        Assertions.assertEquals(endRoomResponse.getRoomId(), roomId);
    }

    @Test
    @DisplayName("그룹 채팅을 종료에 실패합니다_채팅방이 존재하지 않음")
    void end_group_room_채팅방이_존재하지_않음() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);

        //when & then
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            groupRoomService.endGroupRoom(createEndGroupRoomRequest());
        });
    }

    @Test
    @DisplayName("그룹 채팅을 종료에 실패합니다_종료 권한이 없음")
    void end_group_room_종료_권한이_없음() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(true);
        when(groupRoomDetailService.isRoomOwner(any(), any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(CanNotDeleteRoomException.class, () -> {
            groupRoomService.endGroupRoom(createEndGroupRoomRequest());
        });
    }

    @Test
    @DisplayName("그룹 채팅을 종료에 실패합니다_마니또 채팅 진행중")
    void end_group_room_마니또_채팅_진행중() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(roomService.isGroupRoom(any())).thenReturn(true);
        when(groupRoomDetailService.isRoomOwner(any(), any())).thenReturn(true);
        when(manitoRoomDetailService.isExistsManitoRoomsInGroup(any())).thenReturn(true);

        //when & then
        Assertions.assertThrows(CanNotDeleteRoomException.class, () -> {
            groupRoomService.endGroupRoom(createEndGroupRoomRequest());
        });
    }

    private EndGroupRoomRequest createEndGroupRoomRequest() {
        return new EndGroupRoomRequest(
                roomId,
                roomOwnerId
        );
    }
}