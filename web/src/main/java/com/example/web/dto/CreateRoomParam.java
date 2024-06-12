package com.example.web.dto;

import com.example.web.enums.RoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRoomParam {
    private RoomType type;
}
