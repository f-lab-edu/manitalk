package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.dto.GroupRoomDetailDto;
import com.example.web.dto.GroupRoomDto;
import com.example.web.enums.RoomType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vo.GroupRoomDetailVo;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupRoomServiceTest {

    @Mock
    private GroupRoomDetailService groupRoomDetailService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private GroupRoomService groupRoomService;

    @Test
    void create_group_room() throws Exception {

        // given
        Integer roomOwnerId = 1;
        String roomName = "테스트룸";
        String enterCode = "T1234";
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        Room room = new Room();
        setId(room, 1);
        when(roomService.saveRoom(any(Room.class))).thenReturn(room);

        GroupRoomDetailDto groupRoomDetailDto = GroupRoomDetailDto.builder()
                .roomId(room.getId())
                .roomName(dto.getRoomName())
                .roomOwnerId(dto.getRoomOwnerId())
                .roomOwnerId(dto.getRoomOwnerId())
                .enterCode(dto.getEnterCode())
                .build();

        when(groupRoomDetailService.createGroupRoomDetail(any(GroupRoomDetailVo.class)))
                .thenReturn(groupRoomDetailDto);

        // when
        GroupRoomDto groupRoomDto = groupRoomService.createGroupRoom(dto);

        // then
        Assertions.assertEquals(groupRoomDto.getId(), room.getId());
        Assertions.assertEquals(groupRoomDto.getType(), RoomType.G);
        Assertions.assertEquals(groupRoomDto.getGroupRoomDetailDto().getRoomName(), roomName);
    }

    private void setId(Room room, Integer id) throws Exception {
        Field idField = Room.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(room, id);
    }
}