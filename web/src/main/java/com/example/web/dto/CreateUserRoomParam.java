package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRoomParam {
    private Integer userId;
    private Integer roomId;
    private String nickname;
}
