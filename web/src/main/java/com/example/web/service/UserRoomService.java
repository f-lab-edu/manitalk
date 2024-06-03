package com.example.web.service;

import com.example.web.domain.UserRoom;
import com.example.web.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vo.UserRoomVo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepository userRoomRepository;

    public UserRoom saveUserRoom(UserRoomVo userRoomVo) {
        UserRoom userRoom = new UserRoom(
                userRoomVo.getUser(),
                userRoomVo.getRoom(),
                userRoomVo.getNickname()
        );
        return userRoomRepository.save(userRoom);
    }

    public boolean isExistsUserRoom(Integer userId, Integer roomId) {
        Optional<UserRoom> userRoom = findByUserIdAndRoomId(userId, roomId);
        return userRoom.isPresent();
    }

    public Optional<UserRoom> findByUserIdAndRoomId(Integer userId, Integer roomId) {
        return userRoomRepository.findByUserIdAndRoomId(userId, roomId);
    }
}
