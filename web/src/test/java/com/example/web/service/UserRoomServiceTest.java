package com.example.web.service;

import com.example.web.dto.CreateUserRoomsParam;
import com.example.web.repository.fake.FakeUserRoomRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class UserRoomServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private final FakeUserRoomRepository userRoomRepository = new FakeUserRoomRepository();
    private UserRoomService userRoomService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        userRoomService = new UserRoomService(userRoomRepository, entityManager, applicationEventPublisher);
    }

    @Test
    @DisplayName("8명의 사용자를 4개의 마니또 채팅방에 분배합니다.")
    void createUserRooms() {
        // given
        int count = 4;
        List<Integer> roomIds = new ArrayList<>();
        Map<Integer, Integer> pairs = new HashMap<>();
        for (int i = 1; i < count + 1; i++) {
            roomIds.add(i);
            pairs.put(i, i + 1);
        }

        CreateUserRoomsParam createUserRoomsParam = CreateUserRoomsParam.builder()
                .roomIds(roomIds)
                .pairs(pairs)
                .build();

        // when
        List<Integer> userRoomIds = userRoomService.createUserRooms(createUserRoomsParam);

        // then
        Assertions.assertEquals(userRoomIds.size(), count * 2);
    }
}