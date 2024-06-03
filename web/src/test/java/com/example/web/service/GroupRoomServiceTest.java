package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.Room;
import com.example.web.domain.User;
import com.example.web.domain.UserRoom;
import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedUserRoomException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
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
import com.example.web.vo.GroupRoomDetailVo;

import java.lang.reflect.Field;
import java.util.Optional;

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

    @InjectMocks
    private GroupRoomService groupRoomService;

    Integer userId = 1;

    Integer roomId = 1;

    Integer roomOwnerId = 1;

    String roomName = "test_room";

    String enterCode = "test_code";

    String nickname = "test";

    Room room;

    GroupRoomDetail groupRoomDetail;

    User user;

    UserRoom userRoom;

    EnterGroupRoomDto enterGroupRoomDto;

    @BeforeEach
    public void setUp() throws Exception {
        room = new Room();
        setRoomId(room, roomId);

        groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setEnterCode(enterCode);
        room.setGroupRoomDetail(groupRoomDetail);

        user = new User();
        setUserId(user, userId);

        userRoom = new UserRoom(
                user,
                room,
                nickname
        );

        enterGroupRoomDto = new EnterGroupRoomDto(
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
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        when(roomService.saveRoom(any(RoomVo.class))).thenReturn(room);

        GroupRoomDetailDto groupRoomDetailDto = GroupRoomDetailDto.builder()
                .roomId(room.getId())
                .roomName(dto.getRoomName())
                .roomOwnerId(dto.getRoomOwnerId())
                .roomOwnerId(dto.getRoomOwnerId())
                .enterCode(dto.getEnterCode())
                .build();

        when(groupRoomDetailService.createGroupRoomDetail(any(GroupRoomDetailVo.class)))
                .thenReturn(groupRoomDetailDto);

        // when
        GroupRoomDto groupRoomDto = groupRoomService.createGroupRoom(dto);

        // then
        Assertions.assertEquals(groupRoomDto.getId(), room.getId());
        Assertions.assertEquals(groupRoomDto.getType(), RoomType.G);
        Assertions.assertEquals(groupRoomDto.getGroupRoomDetailDto().getRoomName(), roomName);
    }

    @Test
    @DisplayName("그룹 채팅방에 입장합니다.")
    void enter_group_room() {

        //given
        when(roomService.findByRoomId(any())).thenReturn(Optional.of(room));
        when(userService.findByUserId(any())).thenReturn(Optional.of(user));
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(false);
        when(userRoomService.saveUserRoom(any(UserRoomVo.class))).thenReturn(userRoom);

        //when
        UserRoomDto userRoomDto = groupRoomService.enterGroupRoom(enterGroupRoomDto);

        //then
        Assertions.assertEquals(userRoomDto.getRoomId(), roomId);
        Assertions.assertEquals(userRoomDto.getUserId(), userId);
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. : 채팅방을 찾을 수 없음")
    void enter_group_room_채팅방을_찾을_수_없음() {

        //given
        when(roomService.findByRoomId(any())).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. : 입장코드 불일치")
    void enter_group_room_입장코드_불일치() {

        //given
        groupRoomDetail.setEnterCode("test_x");
        room.setGroupRoomDetail(groupRoomDetail);
        when(roomService.findByRoomId(any())).thenReturn(Optional.of(room));

        //when & then
        Assertions.assertThrows(CanNotEnterRoomException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. : 사용자를 찾을 수 없음")
    void enter_group_room_사용자를_찾을_수_없음() {

        //given
        when(roomService.findByRoomId(any())).thenReturn(Optional.of(room));
        when(userService.findByUserId(any())).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. : 이미 입장한 사용자")
    void enter_group_room_이미_입장한_사용자() {

        //given
        when(roomService.findByRoomId(any())).thenReturn(Optional.of(room));
        when(userService.findByUserId(any())).thenReturn(Optional.of(user));
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(true);

        //when & then
        Assertions.assertThrows(DuplicatedUserRoomException.class, () -> {
            groupRoomService.enterGroupRoom(enterGroupRoomDto);
        });
    }

    private void setUserId(User user, Integer id) throws Exception {
        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);
    }

    private void setRoomId(Room room, Integer id) throws Exception {
        Field idField = Room.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(room, id);
    }
}