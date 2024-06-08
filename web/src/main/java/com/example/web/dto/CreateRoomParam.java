package com.example.web.dto;

import com.example.web.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRoomParam {
    private RoomType type;
}
