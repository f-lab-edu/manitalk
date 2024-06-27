package com.example.web.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoomVo {
    private Integer id;
    private String nickname;
}
