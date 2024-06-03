package com.example.web.vo;

import com.example.web.domain.Room;
import com.example.web.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRoomVo {
    private User user;
    private Room room;
    private String nickname;
}
