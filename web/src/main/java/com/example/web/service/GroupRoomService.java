package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.event.EndRoomEvent;
import com.example.web.event.EnterRoomEvent;
import com.example.web.exception.room.*;
import com.example.web.exception.user.UserNotFoundException;
import com.example.web.dto.CreateRoomParam;
import com.example.web.vo.GroupRoomDetailVo;
import com.example.web.vo.RoomVo;
import com.example.web.vo.UserRoomVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.web.dto.CreateGroupRoomDetailParam;
import com.example.web.dto.CreateUserRoomParam;

@Service
@RequiredArgsConstructor
public class GroupRoomService {

    private final RoomService roomService;
    private final GroupRoomDetailService groupRoomDetailService;
    private final ManitoRoomDetailService manitoRoomDetailService;
    private final UserService userService;
    private final UserRoomService userRoomService;
    private final ApplicationEventPublisher applicationEventPublisher;

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

        // 그룹 채팅방 입장 이벤트를 발행합니다.
        applicationEventPublisher.publishEvent(new EnterRoomEvent(dto.getRoomId(), RoomType.G, dto.getUserId(), dto.getNickname()));

        return EnterRoomResponse.builder()
                .userRoomId(userRoomVo.getId())
                .nickname(userRoomVo.getNickname())
                .build();
    }

    @Transactional
    public EndRoomResponse endGroupRoom(EndGroupRoomRequest dto) {

        if (!roomService.isExistsRoom(dto.getRoomId()) ||
                !roomService.isGroupRoom(dto.getRoomId())) {
            throw new RoomNotFoundException("그룹 채팅방이 존재하지 않습니다.");
        }

        if (!groupRoomDetailService.isRoomOwner(dto.getRoomId(), dto.getRoomOwnerId())) {
            throw new CanNotDeleteRoomException("그룹 채팅 종료 권한이 없습니다.");
        }

        if (manitoRoomDetailService.isExistsManitoRoomsInGroup(dto.getRoomId())) {
            throw new CanNotDeleteRoomException("마니또 채팅이 진행중일 때는 그룹 채팅을 종료할 수 없습니다.");
        }

        // 채팅방에 속한 유저 데이터를 삭제합니다.
        userRoomService.softDeleteByRoomId(dto.getRoomId());

        // 채팅방 데이터를 삭제합니다.
        groupRoomDetailService.softDeleteByRoomId(dto.getRoomId());
        roomService.softDeleteById(dto.getRoomId());

        // 데이터 삭제 성공 시의 종료 이벤트를 발행합니다.
        applicationEventPublisher.publishEvent(new EndRoomEvent(dto.getRoomId()));

        return EndRoomResponse.builder()
                .roomId(dto.getRoomId())
                .build();
    }
}
