package com.example.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class UserVo {
    private Integer id;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVo userVo = (UserVo) o;
        return Objects.equals(id, userVo.id) && Objects.equals(email, userVo.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
