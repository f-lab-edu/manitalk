package com.example.web.dto;

import com.example.web.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupRoomDto {
    private Integer id;
    private RoomType type;
    private GroupRoomDetailDto groupRoomDetailDto;
}
