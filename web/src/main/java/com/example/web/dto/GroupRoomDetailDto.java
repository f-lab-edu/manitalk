package com.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupRoomDetailDto {
    private Integer roomId;
    private String roomName;
    private Integer roomOwnerId;
    private String enterCode;
}
