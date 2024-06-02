package com.example.web.service;

import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.domain.Room;
import com.example.web.dto.GroupRoomDetailDto;
import com.example.web.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vo.GroupRoomDetailVo;
import com.example.web.dto.GroupRoomDto;

@Service
@RequiredArgsConstructor
public class GroupRoomService {

    private final RoomRepository roomRepository;
    private final GroupRoomDetailService groupRoomDetailService;

    @Transactional
    public GroupRoomDto createGroupRoom(CreateGroupRoomDto dto) {
        Room room = roomRepository.save(new Room());

        GroupRoomDetailVo groupRoomDetailVo = new GroupRoomDetailVo(
                room,
                dto.getRoomOwnerId(),
                dto.getRoomName(),
                dto.getEnterCode()
        );

        GroupRoomDetailDto groupRoomDetailDto = groupRoomDetailService.createGroupRoomDetail(groupRoomDetailVo);
        return new GroupRoomDto(
                room.getId(),
                room.getType(),
                groupRoomDetailDto
        );
    }
}
