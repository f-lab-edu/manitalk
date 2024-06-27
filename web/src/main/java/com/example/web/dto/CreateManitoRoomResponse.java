package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateManitoRoomResponse {
    private Integer groupRoomId;
    private Integer manitoRoomCount;
}
