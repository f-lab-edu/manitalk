package com.example.web.controller;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.Room;
import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.service.GroupChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupChatService groupChatService;

    @Test
    public void create_group_room_성공() throws Exception {
        Integer roomOwnerId = 1;
        String roomName = "테스트룸";
        String enterCode = "T1234";
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        Room mockRoom = new Room();
        GroupRoomDetail mockGroupRoomDetail = new GroupRoomDetail(mockRoom);
        mockGroupRoomDetail.setRoomOwner(roomOwnerId);
        mockGroupRoomDetail.setRoomName(roomName);
        mockGroupRoomDetail.setEnterCode(enterCode);
        mockRoom.setGroupRoomDetail(mockGroupRoomDetail);

        Mockito.when(groupChatService.createGroupRoom(Mockito.any(CreateGroupRoomDto.class))).thenReturn(mockRoom);

        mockMvc.perform(post("/group/room")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupRoomDetail.roomName").value(roomName));
    }

    @Test
    public void create_group_room_유효성검사_실패() throws Exception {
        Integer roomOwnerId = null;
        String roomName = "테스트룸";
        String enterCode = "T1234";
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        Room mockRoom = new Room();
        GroupRoomDetail mockGroupRoomDetail = new GroupRoomDetail(mockRoom);
        mockGroupRoomDetail.setRoomOwner(roomOwnerId);
        mockGroupRoomDetail.setRoomName(roomName);
        mockGroupRoomDetail.setEnterCode(enterCode);
        mockRoom.setGroupRoomDetail(mockGroupRoomDetail);

        Mockito.when(groupChatService.createGroupRoom(Mockito.any(CreateGroupRoomDto.class))).thenReturn(mockRoom);

        mockMvc.perform(post("/group/room")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
