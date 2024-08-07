package com.example.web.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoomVo {
    private Integer id;
    private Integer userId;
    private Integer roomId;
    private String nickname;
}
