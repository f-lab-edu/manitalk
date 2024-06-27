package com.example.web.dto;

import com.example.web.enums.RoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRoomsParam {
    private RoomType type;
    private Integer count;
}
