package com.example.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class UserRoomVo {
    private Integer id;
    private String nickname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoomVo that = (UserRoomVo) o;
        return Objects.equals(id, that.id) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname);
    }
}
