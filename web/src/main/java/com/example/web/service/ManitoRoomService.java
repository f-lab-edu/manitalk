package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.event.EnterRoomEvent;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ManitoRoomService {

    private final RoomService roomService;
    private final ManitoRoomDetailService manitoRoomDetailService;
    private final UserRoomService userRoomService;
    private final ManitoMissionService manitoMissionService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public CreateManitoRoomResponse createManitoRooms(CreateManitoRoomRequest dto) {

        // TODO: 유저 세션을 통해 요청자가 그룹 채팅방의 방장인지 확인한다.

        if (manitoRoomDetailService.isExistsManitoRoomsInGroup(dto.getGroupRoomId())) {
            throw new DuplicatedRoomException("이미 진행중인 마니또 채팅이 있습니다.");
        }

        // 그룹 채팅방의 전체 멤버 ID를 구한다.
        List<Integer> groupRoomMembers = userRoomService.getUserIdsByRoomId(dto.getGroupRoomId());

        // 멤버를 랜덤으로 1:1 매칭한다.
        Map<Integer, Integer> pairs = makeMemberPairs(groupRoomMembers);

        // 멤버 매칭 그룹별로 하나의 마니또 채팅방을 생성한다.
        CreateRoomsParam createRoomsParam = CreateRoomsParam.builder()
                .type(RoomType.M)
                .count(pairs.size())
                .build();
        List<Integer> roomIds = roomService.createRooms(createRoomsParam);

        CreateManitoRoomDetailsParam createManitoRoomDetailsParam = CreateManitoRoomDetailsParam.builder()
                .groupRoomId(dto.getGroupRoomId())
                .roomIds(roomIds)
                .expiresDays(dto.getExpiresDays())
                .build();
        manitoRoomDetailService.createManitoRoomDetails(createManitoRoomDetailsParam);

        // 채팅방과 멤버 연결 데이터를 저장한다.
        CreateUserRoomsParam createUserRoomsParam = CreateUserRoomsParam.builder()
                .roomIds(roomIds)
                .pairs(pairs)
                .build();
        List<Integer> userRoomIds = userRoomService.createUserRooms(createUserRoomsParam);

        // 멤버 별로 마니또 채팅 미션을 지정한다.
        manitoMissionService.createUserRoomMissions(userRoomIds);

        return CreateManitoRoomResponse.builder()
                .groupRoomId(dto.getGroupRoomId())
                .manitoRoomCount(pairs.size())
                .build();
    }

    private Map<Integer, Integer> makeMemberPairs(List<Integer> groupRoomMembers) {

        Collections.shuffle(groupRoomMembers);

        Map<Integer, Integer> pairs = new HashMap<>();

        int roomSize = groupRoomMembers.size();
        for (int i = 0; i < roomSize - 1; i += 2) {
            pairs.put(groupRoomMembers.get(i), groupRoomMembers.get(i + 1));
        }

        if (roomSize % 2 == 1) {
            // 멤버의 수가 홀수인 경우, 한 멤버가 2개의 채팅을 진행한다.
            int random = new Random().nextInt(roomSize - 1);
            pairs.put(groupRoomMembers.getLast(), groupRoomMembers.get(random));
        }

        return pairs;
    }

    @Transactional
    public EnterManitoRoomResponse enterManitoRoom(EnterManitoRoomRequest dto) {

        if (!userRoomService.isExistsUserRoom(dto.getUserId(), dto.getRoomId())) {
            throw new CanNotEnterRoomException("채팅방의 멤버가 아닙니다.");
        }

        Integer userRoomId = userRoomService.getUserRoomId(dto.getUserId(), dto.getRoomId());

        // 닉네임을 설정합니다.
        userRoomService.setNicknameByUserRoomId(userRoomId, dto.getNickname());

        // 마니또 채팅방 입장 이벤트를 발행합니다.
        applicationEventPublisher.publishEvent(new EnterRoomEvent(dto.getRoomId(),RoomType.M, dto.getUserId(), dto.getNickname()));

        return EnterManitoRoomResponse.builder()
                .userRoomId(userRoomId)
                .build();

    }
}
