package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedUserRoomException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
import com.example.web.dto.CreateRoomParam;
import com.example.web.vo.GroupRoomDetailVo;
import com.example.web.vo.RoomVo;
import com.example.web.vo.UserRoomVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.web.dto.CreateGroupRoomDetailParam;
import com.example.web.dto.CreateUserRoomParam;

@Service
@RequiredArgsConstructor
public class GroupRoomService {

    private final RoomService roomService;
    private final GroupRoomDetailService groupRoomDetailService;
    private final UserService userService;
    private final UserRoomService userRoomService;

    @Transactional
    public CreateGroupRoomResponse createGroupRoom(CreateGroupRoomRequest dto) {

        CreateRoomParam createRoomParam = CreateRoomParam.builder().type(RoomType.G).build();
        RoomVo roomVo = roomService.createRoom(createRoomParam);

        CreateGroupRoomDetailParam createGroupRoomDetailParam = CreateGroupRoomDetailParam.builder()
                .roomId(roomVo.getId())
                .roomOwnerId(dto.getRoomOwnerId())
                .roomName(dto.getRoomName())
                .enterCode(dto.getEnterCode())
                .build();

        GroupRoomDetailVo groupRoomDetailVo = groupRoomDetailService.createGroupRoomDetail(createGroupRoomDetailParam);

        return CreateGroupRoomResponse.builder()
                .id(roomVo.getId())
                .roomType(roomVo.getRoomType())
                .roomName(groupRoomDetailVo.getRoomName())
                .roomOwnerId(groupRoomDetailVo.getRoomOwnerId())
                .enterCode(groupRoomDetailVo.getEnterCode())
                .build();
    }

    @Transactional
    public EnterRoomResponse enterGroupRoom(EnterGroupRoomRequest dto) {

        if (!roomService.isExistsRoom(dto.getRoomId())) {
            throw new RoomNotFoundException("채팅방이 존재하지 않습니다.");
        }

        if (!roomService.isGroupRoom(dto.getRoomId())) {
            throw new RoomNotFoundException("그룹 채팅이 아닙니다.");
        }

        if (!groupRoomDetailService.isRightEnterCode(dto.getRoomId(), dto.getEnterCode())) {
            throw new CanNotEnterRoomException("입장 코드가 맞지 않습니다.");
        }

        if (!userService.isExistsUser(dto.getUserId())) {
            throw new UserNotFoundException("채팅방에 입장할 사용자를 찾을 수 없습니다.");
        }

        if (userRoomService.isExistsUserRoom(dto.getUserId(), dto.getRoomId())) {
            throw new DuplicatedUserRoomException("이미 입장한 사용자 입니다.");
        }

        CreateUserRoomParam createUserRoomParam = CreateUserRoomParam.builder()
                .userId(dto.getUserId())
                .roomId(dto.getRoomId())
                .nickname(dto.getNickname())
                .build();
        UserRoomVo userRoomVo = userRoomService.createUserRoom(createUserRoomParam);

        return EnterRoomResponse.builder()
                .userRoomId(userRoomVo.getId())
                .nickname(userRoomVo.getNickname())
                .build();
    }
}
