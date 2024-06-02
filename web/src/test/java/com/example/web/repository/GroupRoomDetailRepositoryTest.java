package com.example.web.repository;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupRoomDetailRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GroupRoomDetailRepository groupRoomDetailRepository;

    @Test
    public void create_group_room_detail() {
        Room newRoom = roomRepository.save(new Room());

        Integer roomOwnerId = 1;
        String roomName = "테스트룸";
        String enterCode = "T1234";

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(newRoom);
        groupRoomDetail.setRoomOwnerId(roomOwnerId);
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        GroupRoomDetail newGroupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        newRoom.setGroupRoomDetail(newGroupRoomDetail);

        Assertions.assertEquals(newRoom.getType(), RoomType.G);
        Assertions.assertEquals(newGroupRoomDetail.getRoomName(), roomName);
        Assertions.assertNotNull(newGroupRoomDetail.getRoomId());
        Assertions.assertSame(newRoom.getId(), newGroupRoomDetail.getRoomId());
    }
}
