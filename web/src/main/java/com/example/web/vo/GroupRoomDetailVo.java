package com.example.web.vo;

import com.example.web.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupRoomDetailVo {
    private Room room;
    private Integer roomOwnerId;
    private String roomName;
    private String enterCode;
}
