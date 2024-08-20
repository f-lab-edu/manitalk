package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.domain.User;
import com.example.web.domain.UserRoom;
import com.example.web.dto.CreateUserRoomsParam;
import com.example.web.event.CreateManitoRoomEvent;
import com.example.web.repository.jpa.UserRoomRepository;
import com.example.web.vo.UserRoomVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserRoomVo createUserRoom(CreateUserRoomParam param) {
        UserRoom userRoom = makeUserRoom(param.getUserId(), param.getRoomId(), param.getNickname());
        userRoom = userRoomRepository.save(userRoom);

        return new UserRoomVo(userRoom.getId(), param.getUserId(), param.getRoomId(), userRoom.getNickname());
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

            // 마니또 채팅방 생성 이벤트를 발행합니다.
            applicationEventPublisher.publishEvent(new CreateManitoRoomEvent(firstUserId, roomId));
            applicationEventPublisher.publishEvent(new CreateManitoRoomEvent(secondUserId, roomId));

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

    public List<Integer> getUserIdsByRoomId(Integer roomId) {
        return userRoomRepository.findUserIdsByRoomId(roomId);
    }

    public void softDeleteByRoomId(Integer roomId) {
        userRoomRepository.findByRoomId(roomId).forEach(
                userRoom -> {
                    userRoom.setDeleted(true);
                    userRoomRepository.save(userRoom);
                }
        );
    }

    public void softDeleteAllByRoomIds(List<Integer> roomIds) {
        List<UserRoom> userRooms = userRoomRepository.findAllByRoomIdIn(roomIds);
        userRooms.forEach(manitoRoomDetail -> manitoRoomDetail.setDeleted(true));
        userRoomRepository.saveAll(userRooms);
    }

    public Integer getUserRoomId(Integer userId, Integer roomId) {
        return userRoomRepository.findIdByUserIdAndRoomId(userId, roomId);
    }

    public void setNicknameByUserRoomId(Integer userRoomId, String nickname) {
        UserRoom userRoom = userRoomRepository.findById(userRoomId).orElse(null);
        if (userRoom != null) {
            userRoom.setNickname(nickname);
            userRoomRepository.save(userRoom);
        }
    }

    public List<UserRoomVo> getUserRoomList(List<Integer> roomIds) {
        List<UserRoom> userRooms = userRoomRepository.findAllByRoomIdIn(roomIds);
        return userRooms.stream()
                .map(userRoom -> new UserRoomVo(
                        userRoom.getId(),
                        userRoom.getUser().getId(),
                        userRoom.getRoom().getId(),
                        userRoom.getNickname()))
                .collect(Collectors.toList());
    }
}
