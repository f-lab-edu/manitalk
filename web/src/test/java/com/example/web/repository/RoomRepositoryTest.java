package com.example.web.repository;

import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void create_group_room() {
        Room room = new Room();
        Room newRoom = roomRepository.save(room);

        Assertions.assertEquals(newRoom.getType(), RoomType.G);
    }
}
