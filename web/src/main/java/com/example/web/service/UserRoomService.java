package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.domain.User;
import com.example.web.domain.UserRoom;
import com.example.web.dto.CreateUserRoomsParam;
import com.example.web.repository.UserRoomRepository;
import com.example.web.vo.UserRoomVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.web.dto.CreateUserRoomParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepository userRoomRepository;

    private final EntityManager entityManager;

    public UserRoomVo createUserRoom(CreateUserRoomParam param) {
        UserRoom userRoom = makeUserRoom(param.getUserId(), param.getRoomId(), param.getNickname());
        userRoom = userRoomRepository.save(userRoom);

        return createUserRoomVo(userRoom);
    }

    public List<Integer> createUserRooms(CreateUserRoomsParam param) {
        List<UserRoom> userRooms = makeUserRoomList(param);
        userRooms = userRoomRepository.saveAll(userRooms);

        return userRooms.stream()
                .map(UserRoom::getId)
                .collect(Collectors.toList());
    }

    private List<UserRoom> makeUserRoomList(CreateUserRoomsParam param) {
        List<UserRoom> userRooms = new ArrayList<>();

        AtomicInteger i = new AtomicInteger();
        param.getPairs().forEach((firstUserId, secondUserId) -> {
            int roomId = param.getRoomIds().get(i.get());
            userRooms.add(makeUserRoom(firstUserId, roomId, null));
            userRooms.add(makeUserRoom(secondUserId, roomId, null));

            i.getAndIncrement();
        });

        return userRooms;
    }

    private UserRoom makeUserRoom(Integer userId, Integer roomId, String nickname) {
        User user = entityManager.getReference(User.class, userId);
        Room room = entityManager.getReference(Room.class, roomId);

        return new UserRoom(user, room, nickname);
    }

    public boolean isExistsUserRoom(Integer userId, Integer roomId) {
        return userRoomRepository.existsByUserIdAndRoomId(userId, roomId);
    }

    public List<Integer> getRoomMembers(Integer roomId) {
        return userRoomRepository.findUserIdByRoomId(roomId);
    }

    private UserRoomVo createUserRoomVo(UserRoom userRoom) {
        return new UserRoomVo(userRoom.getId(), userRoom.getNickname());
    }
}
