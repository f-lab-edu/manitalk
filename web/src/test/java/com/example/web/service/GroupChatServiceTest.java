package com.example.web.service;

import com.example.web.domain.Room;
import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.repository.GroupRoomDetailRepository;
import com.example.web.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class GroupChatServiceTest {

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GroupRoomDetailRepository groupRoomDetailRepository;

    @Transactional
    @Test
    public void create_group_room() {
        Integer roomOwnerId = 1;
        String roomName = "테스트룸";
        String enterCode = "T1234";

        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);
        Room room = groupChatService.createGroupRoom(dto);

        Assertions.assertEquals(room.getGroupRoomDetail().getRoomName(), roomName);
    }
}
