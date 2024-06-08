package com.example.web.dto;

import com.example.web.domain.Room;
import com.example.web.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRoomParam {
    private User user;
    private Room room;
    private String nickname;
}
