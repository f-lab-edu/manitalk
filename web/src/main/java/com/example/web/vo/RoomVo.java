package com.example.web.vo;

import com.example.web.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class RoomVo {
    private Integer id;
    private RoomType roomType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomVo roomVo = (RoomVo) o;
        return Objects.equals(id, roomVo.id) && roomType == roomVo.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomType);
    }
}
