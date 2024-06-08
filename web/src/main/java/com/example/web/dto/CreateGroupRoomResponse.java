package com.example.web.dto;

import com.example.web.enums.RoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateGroupRoomResponse {
    private Integer id;
    private RoomType type;
    private CreateGroupRoomDetailResponse createGroupRoomDetailResponse;
}
