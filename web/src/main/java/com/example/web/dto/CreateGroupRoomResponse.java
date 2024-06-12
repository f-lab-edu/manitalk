package com.example.web.dto;

import com.example.web.enums.RoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateGroupRoomResponse {
    private Integer id;
    private RoomType roomType;
    private String roomName;
    private Integer roomOwnerId;
    private String enterCode;
}
