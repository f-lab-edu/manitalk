package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.domain.User;
import com.example.web.domain.UserRoom;
import com.example.web.repository.UserRoomRepository;
import com.example.web.vo.UserRoomVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoomServiceTest {

    @Mock
    private UserRoomRepository userRoomRepository;

    @InjectMocks
    private UserRoomService userRoomService;

    Integer userId = 1;

    Integer roomId = 1;

    UserRoomVo userRoomVo;

    UserRoom userRoom;

    String nickname = "test";

    @BeforeEach
    public void setUp() throws Exception {
        User user = new User();
        setUserId(user, userId);

        Room room = new Room();
        setRoomId(room, roomId);

        userRoomVo = new UserRoomVo(
                user,
                room,
                nickname
        );

        userRoom = new UserRoom(
                userRoomVo.getUser(),
                userRoomVo.getRoom(),
                userRoomVo.getNickname()
        );
    }

    @Test
    @DisplayName("사용자-채팅방 관계를 생성합니다.")
    void saveUserRoom() {
        // given
        when(userRoomRepository.save(any(UserRoom.class))).thenReturn(userRoom);

        // when
        UserRoom newUserRoom = userRoomService.saveUserRoom(userRoomVo);

        // then
        Assertions.assertEquals(newUserRoom.getUser().getId(), userId);
        Assertions.assertEquals(newUserRoom.getRoom().getId(), roomId);
    }

    @Test
    @DisplayName("사용자-채팅방 관계가 존재하는지 확인합니다.")
    void isExistsUserRoom() {
        // given
        when(userRoomRepository.findByUserIdAndRoomId(any(), any())).thenReturn(Optional.of(userRoom));

        // when
        boolean isExistsUserRoom = userRoomService.isExistsUserRoom(userId, roomId);

        // then
        Assertions.assertTrue(isExistsUserRoom);
    }

    @Test
    @DisplayName("사용자 ID, 채팅방 ID로 사용자-채팅방 관계를 조회합니다.")
    void findByUserIdAndRoomId() {
        // given
        when(userRoomRepository.findByUserIdAndRoomId(any(), any())).thenReturn(Optional.of(userRoom));

        // when
        Optional<UserRoom> findUserRoom = userRoomService.findByUserIdAndRoomId(userId, roomId);

        // then
        Assertions.assertTrue(findUserRoom.isPresent());
    }

    private void setUserId(User user, Integer id) throws Exception {
        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);
    }

    private void setRoomId(Room room, Integer id) throws Exception {
        Field idField = Room.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(room, id);
    }
}