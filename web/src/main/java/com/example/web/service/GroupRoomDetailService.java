package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.dto.GroupRoomDetailDto;
import com.example.web.repository.GroupRoomDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.web.vo.GroupRoomDetailVo;

@Service
@RequiredArgsConstructor
public class GroupRoomDetailService {

    private final GroupRoomDetailRepository groupRoomDetailRepository;

    public GroupRoomDetailDto createGroupRoomDetail(GroupRoomDetailVo vo) {
        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(vo.getRoom());
        groupRoomDetail.setRoomName(vo.getRoomName());
        groupRoomDetail.setRoomOwnerId(vo.getRoomOwnerId());
        groupRoomDetail.setEnterCode(vo.getEnterCode());

        GroupRoomDetail newGroupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        return GroupRoomDetailDto.builder()
                .roomId(vo.getRoom().getId())
                .roomName(newGroupRoomDetail.getRoomName())
                .roomOwnerId(newGroupRoomDetail.getRoomOwnerId())
                .enterCode(newGroupRoomDetail.getEnterCode())
                .build();
    }
}
