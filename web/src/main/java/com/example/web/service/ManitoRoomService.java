package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.exception.room.DuplicatedRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ManitoRoomService {

    private final RoomService roomService;
    private final ManitoRoomDetailService manitoRoomDetailService;
    private final UserRoomService userRoomService;

    @Transactional
    public CreateManitoRoomResponse createManitoRooms(CreateManitoRoomRequest dto) {

        // TODO: 유저 세션을 통해 요청자가 그룹 채팅방의 방장인지 확인한다.

        if (manitoRoomDetailService.isExistsManitoRoomsInGroup(dto.getGroupRoomId())) {
            throw new DuplicatedRoomException("이미 진행중인 마니또 채팅이 있습니다.");
        }

        // 그룹 채팅방의 전체 멤버 ID를 구한다.
        List<Integer> groupRoomMembers = userRoomService.getRoomMembers(dto.getGroupRoomId());

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
        userRoomService.createUserRooms(createUserRoomsParam);

        // TODO: 생성한 채팅방에 대한 event message 를 전송한다.(websocket)

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
}
