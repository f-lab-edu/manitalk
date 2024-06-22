package com.example.web.service;

import com.example.web.dto.CreateManitoRoomDetailsParam;
import com.example.web.repository.fake.FakeManitoRoomDetailRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ManitoRoomDetailServiceTest {

    @Mock
    private EntityManager entityManager;

    private ManitoRoomDetailService manitoRoomDetailService;

    Integer groupRoomId = 1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        manitoRoomDetailService = new ManitoRoomDetailService(new FakeManitoRoomDetailRepository(), entityManager);
    }

    @Test
    @DisplayName("전체 마니또 채팅방의 상세정보를 저장합니다.")
    void create_manito_room_details() {

        //given
        int count = 4;

        List<Integer> roomIds = new ArrayList<>();
        for (int i = 1; i < count + 1; i++) {
            roomIds.add(i);
        }

        CreateManitoRoomDetailsParam createManitoRoomDetailsParam = CreateManitoRoomDetailsParam.builder()
                .groupRoomId(groupRoomId)
                .roomIds(roomIds)
                .expiresDays(1L)
                .build();

        // when
        List<Integer> manitoRoomIds = manitoRoomDetailService.createManitoRoomDetails(createManitoRoomDetailsParam);

        // then
        Assertions.assertEquals(manitoRoomIds.size(), count);
    }
}
