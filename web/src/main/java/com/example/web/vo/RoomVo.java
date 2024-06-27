package com.example.web.vo;

import com.example.web.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RoomVo {
    private Integer id;
    private RoomType roomType;
}
