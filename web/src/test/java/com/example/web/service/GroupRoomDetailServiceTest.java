package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.repository.fake.FakeGroupRoomDetailRepository;
import com.example.web.vo.GroupRoomDetailVo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.web.dto.CreateGroupRoomDetailParam;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupRoomDetailServiceTest {

    @Mock
    private EntityManager entityManager;

    private GroupRoomDetailService groupRoomDetailService;

    Integer roomId = 1;

    Integer roomOwnerId = 1;

    String roomName = "test_room";

    String enterCode = "test_code";

    Room room;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        groupRoomDetailService = new GroupRoomDetailService(new FakeGroupRoomDetailRepository(), entityManager);

        room = new Room();
    }

    @Test
    @DisplayName("그룹 채팅방의 상세정보를 저장합니다.")
    void create_group_room_detail() {

        //given
        when(entityManager.getReference(eq(Room.class), any())).thenReturn(room);

        CreateGroupRoomDetailParam createGroupRoomDetailParam = CreateGroupRoomDetailParam.builder()
                .roomId(roomId)
                .roomOwnerId(roomOwnerId)
                .roomName(roomName)
                .enterCode(enterCode)
                .build();

        // when
        GroupRoomDetailVo groupRoomDetailVo = groupRoomDetailService.createGroupRoomDetail(createGroupRoomDetailParam);

        // then
        Assertions.assertEquals(groupRoomDetailVo.getRoomName(), roomName);
        Assertions.assertEquals(groupRoomDetailVo.getRoomOwnerId(), roomOwnerId);
    }
}
