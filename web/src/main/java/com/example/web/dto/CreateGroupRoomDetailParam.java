package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateGroupRoomDetailParam {
    private Integer roomId;
    private Integer roomOwnerId;
    private String roomName;
    private String enterCode;
}
