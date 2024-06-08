package com.example.web.dto;

import com.example.web.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateGroupRoomDetailParam {
    private Room room;
    private Integer roomOwnerId;
    private String roomName;
    private String enterCode;
}
