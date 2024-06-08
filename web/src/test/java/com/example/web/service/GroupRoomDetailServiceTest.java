package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.Room;
import com.example.web.dto.CreateGroupRoomDetailResponse;
import com.example.web.repository.GroupRoomDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.web.dto.CreateGroupRoomDetailParam;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupRoomDetailServiceTest {

    @Mock
    private GroupRoomDetailRepository groupRoomDetailRepository;

    @InjectMocks
    private GroupRoomDetailService groupRoomDetailService;

    Integer roomOwnerId = 1;

    String roomName = "test_room";

    String enterCode = "test_code";

    Room room;

    @BeforeEach
    public void setUp() throws Exception {
        room = new Room();
        setId(room, 1);
    }

    @Test
    @DisplayName("새로운 그룹 채팅방을 생성합니다.")
    void create_group_room_detail() {

        //given
        CreateGroupRoomDetailParam createGroupRoomDetailParam = new CreateGroupRoomDetailParam(room, roomOwnerId, roomName, enterCode);

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setRoomOwnerId(roomOwnerId);
        groupRoomDetail.setEnterCode(enterCode);

        when(groupRoomDetailRepository.save(any())).thenReturn(groupRoomDetail);

        // when
        CreateGroupRoomDetailResponse createGroupRoomDetailResponse = groupRoomDetailService.createGroupRoomDetail(createGroupRoomDetailParam);

        // then
        Assertions.assertEquals(createGroupRoomDetailResponse.getRoomId(), room.getId());
        Assertions.assertEquals(createGroupRoomDetailResponse.getRoomName(), roomName);
        Assertions.assertEquals(createGroupRoomDetailResponse.getRoomOwnerId(), roomOwnerId);
    }

    private void setId(Room room, Integer id) throws Exception {
        Field idField = Room.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(room, id);
    }
}