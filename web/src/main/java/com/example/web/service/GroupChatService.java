package com.example.web.service;

import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.Room;
import com.example.web.repository.GroupRoomDetailRepository;
import com.example.web.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupChatService {

    private final RoomRepository roomRepository;
    private final GroupRoomDetailRepository groupRoomDetailRepository;

    @Transactional
    public Room createGroupRoom(CreateGroupRoomDto dto) {
        Room room = roomRepository.save(new Room());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomName(dto.getRoomName());
        groupRoomDetail.setRoomOwner(dto.getRoomOwnerId());
        groupRoomDetail.setEnterCode(dto.getEnterCode());
        GroupRoomDetail newGroupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        room.setGroupRoomDetail(newGroupRoomDetail);
        return room;
    }
}
