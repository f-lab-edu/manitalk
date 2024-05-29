package com.example.web.repository;

import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    @Test
    public void create_group_room() {
        Room room = new Room();
        Room newRoom = roomRepository.save(room);

        Assertions.assertEquals(newRoom.getType(), RoomType.G);
    }
}
