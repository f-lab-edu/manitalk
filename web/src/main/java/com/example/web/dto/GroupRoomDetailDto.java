package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupRoomDetailDto {
    private Integer roomId;
    private String roomName;
    private Integer roomOwnerId;
    private String enterCode;
}