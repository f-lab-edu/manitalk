package com.example.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class GroupRoomDetailVo {
    private String roomName;
    private Integer roomOwnerId;
    private String enterCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupRoomDetailVo that = (GroupRoomDetailVo) o;
        return Objects.equals(roomName, that.roomName) && Objects.equals(roomOwnerId, that.roomOwnerId) && Objects.equals(enterCode, that.enterCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName, roomOwnerId, enterCode);
    }
}
