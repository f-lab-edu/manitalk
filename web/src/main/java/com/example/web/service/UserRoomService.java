package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.domain.User;
import com.example.web.domain.UserRoom;
import com.example.web.repository.UserRoomRepository;
import com.example.web.vo.UserRoomVo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.web.dto.CreateUserRoomParam;

@Service
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepository userRoomRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UserRoomVo createUserRoom(CreateUserRoomParam param) {
        User user = entityManager.getReference(User.class, param.getUserId());
        Room room = entityManager.getReference(Room.class, param.getRoomId());

        UserRoom userRoom = new UserRoom(
                user,
                room,
                param.getNickname()
        );
        userRoomRepository.save(userRoom);

        return createUserRoomVo(userRoom);
    }

    public boolean isExistsUserRoom(Integer userId, Integer roomId) {
        return userRoomRepository.existsByUserIdAndRoomId(userId, roomId);
    }

    private UserRoomVo createUserRoomVo(UserRoom userRoom) {
        return new UserRoomVo(userRoom.getId(), userRoom.getNickname());
    }
}
