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
import com.example.web.dto.CreateRoomParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.web.dto.CreateGroupRoomDetailParam;
import com.example.web.dto.CreateUserRoomParam;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupRoomService {

    private final RoomService roomService;
    private final GroupRoomDetailService groupRoomDetailService;
    private final UserService userService;
    private final UserRoomService userRoomService;

    @Transactional
    public CreateGroupRoomResponse createGroupRoom(CreateGroupRoomRequest dto) {

        CreateRoomParam createRoomParam = new CreateRoomParam(RoomType.G);
        Room room = roomService.saveRoom(createRoomParam);

        CreateGroupRoomDetailParam createGroupRoomDetailParam = new CreateGroupRoomDetailParam(
                room,
                dto.getRoomOwnerId(),
                dto.getRoomName(),
                dto.getEnterCode()
        );

        CreateGroupRoomDetailResponse createGroupRoomDetailResponse = groupRoomDetailService.createGroupRoomDetail(createGroupRoomDetailParam);

        return CreateGroupRoomResponse.builder()
                .id(room.getId())
                .type(room.getType())
                .createGroupRoomDetailResponse(createGroupRoomDetailResponse)
                .build();
    }

    public EnterRoomResponse enterGroupRoom(EnterGroupRoomRequest dto) {

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

        CreateUserRoomParam createUserRoomParam = new CreateUserRoomParam(
                user.get(),
                room.get(),
                dto.getNickname()
        );

        UserRoom userRoom = userRoomService.saveUserRoom(createUserRoomParam);

        return EnterRoomResponse.builder()
                .userRoomId(userRoom.getId())
                .userId(userRoom.getUser().getId())
                .roomId(userRoom.getRoom().getId())
                .roomType(userRoom.getRoom().getType())
                .nickname(userRoom.getNickname())
                .build();
    }
}
