package com.example.web.service;

import com.example.web.domain.User;
import com.example.web.domain.UserRoom;
import com.example.web.dto.*;
import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedUserRoomException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vo.GroupRoomDetailVo;
import vo.UserRoomVo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupRoomService {

    private final RoomService roomService;
    private final GroupRoomDetailService groupRoomDetailService;
    private final UserService userService;
    private final UserRoomService userRoomService;

    @Transactional
    public GroupRoomDto createGroupRoom(CreateGroupRoomDto dto) {
        Room room = roomService.saveRoom(new Room());

        GroupRoomDetailVo groupRoomDetailVo = new GroupRoomDetailVo(
                room,
                dto.getRoomOwnerId(),
                dto.getRoomName(),
                dto.getEnterCode()
        );

        GroupRoomDetailDto groupRoomDetailDto = groupRoomDetailService.createGroupRoomDetail(groupRoomDetailVo);

        return GroupRoomDto.builder()
                .id(room.getId())
                .type(room.getType())
                .groupRoomDetailDto(groupRoomDetailDto)
                .build();
    }

    public UserRoomDto enterGroupRoom(EnterGroupRoomDto dto) {

        Optional<Room> room = roomService.findByRoomId(dto.getRoomId());
        if (room.isEmpty() || room.get().getType() != RoomType.G) {
            throw new RoomNotFoundException("그룹 채팅방을 찾을 수 없습니다.");
        }

        if (!room.get().getGroupRoomDetail().getEnterCode().equals(dto.getEnterCode())) {
            throw new CanNotEnterRoomException("입장 코드가 맞지 않습니다.");
        }

        Optional<User> user = userService.findByUserId(dto.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("채팅방에 입장할 사용자를 찾을 수 없습니다.");
        }

        if (userRoomService.isExistsUserRoom(dto.getUserId(), dto.getRoomId())) {
            throw new DuplicatedUserRoomException("이미 입장한 사용자 입니다.");
        }

        UserRoomVo userRoomVo = new UserRoomVo(
                user.get(),
                room.get(),
                dto.getNickname()
        );

        UserRoom userRoom = userRoomService.saveUserRoom(userRoomVo);

        return UserRoomDto.builder()
                .userRoomId(userRoom.getId())
                .userId(userRoom.getUser().getId())
                .roomId(userRoom.getRoom().getId())
                .roomType(userRoom.getRoom().getType())
                .nickname(userRoom.getNickname())
                .build();
    }
}
