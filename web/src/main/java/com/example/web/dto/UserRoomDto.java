package com.example.web.dto;

import com.example.web.enums.RoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRoomDto {
    private Integer userRoomId;
    private Integer userId;
    private Integer roomId;
    private RoomType roomType;
    private String nickname;
}
