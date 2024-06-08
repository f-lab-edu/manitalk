package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.dto.CreateGroupRoomDetailResponse;
import com.example.web.repository.GroupRoomDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.web.dto.CreateGroupRoomDetailParam;

@Service
@RequiredArgsConstructor
public class GroupRoomDetailService {

    private final GroupRoomDetailRepository groupRoomDetailRepository;

    public CreateGroupRoomDetailResponse createGroupRoomDetail(CreateGroupRoomDetailParam param) {
        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(param.getRoom());
        groupRoomDetail.setRoomName(param.getRoomName());
        groupRoomDetail.setRoomOwnerId(param.getRoomOwnerId());
        groupRoomDetail.setEnterCode(param.getEnterCode());

        GroupRoomDetail newGroupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        return CreateGroupRoomDetailResponse.builder()
                .roomId(param.getRoom().getId())
                .roomName(newGroupRoomDetail.getRoomName())
                .roomOwnerId(newGroupRoomDetail.getRoomOwnerId())
                .enterCode(newGroupRoomDetail.getEnterCode())
                .build();
    }
}
