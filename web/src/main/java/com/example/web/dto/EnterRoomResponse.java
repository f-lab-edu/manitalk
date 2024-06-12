package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterRoomResponse {
    private Integer userRoomId;
    private String nickname;
}
