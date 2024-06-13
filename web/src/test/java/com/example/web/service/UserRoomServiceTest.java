package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.domain.User;
import com.example.web.repository.UserRoomRepository;
import com.example.web.dto.CreateUserRoomParam;
import com.example.web.vo.UserRoomVo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoomServiceTest {

    @Mock
    private UserRoomRepository userRoomRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserRoomService userRoomService;

    Integer userId = 1;

    Integer roomId = 1;

    String nickname = "test";

    User user;

    Room room;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        user = new User();
        setUserId(user, userId);

        room = new Room();
        setRoomId(room, roomId);
    }

    @Test
    @DisplayName("사용자-채팅방 관계를 생성합니다.")
    void createUserRoom() {
        // given
        when(entityManager.getReference(eq(User.class), any())).thenReturn(user);
        when(entityManager.getReference(eq(Room.class), any())).thenReturn(room);

        CreateUserRoomParam createUserRoomParam = CreateUserRoomParam.builder()
                .userId(userId)
                .roomId(roomId)
                .nickname(nickname)
                .build();

        // when
        UserRoomVo userRoomVo = userRoomService.createUserRoom(createUserRoomParam);

        // then
        Assertions.assertEquals(userRoomVo.getNickname(), nickname);
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