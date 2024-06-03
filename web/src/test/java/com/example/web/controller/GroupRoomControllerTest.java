package com.example.web.controller;

import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.dto.GroupRoomDetailDto;
import com.example.web.dto.GroupRoomDto;
import com.example.web.enums.RoomType;
import com.example.web.service.GroupRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GroupRoomController.class)
@MockBean(JpaMetamodelMappingContext.class)
class GroupRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupRoomService groupRoomService;

    @Test
    void create_group_room() throws Exception {

        // given
        Integer roomOwnerId = 1;
        String roomName = "테스트룸";
        String enterCode = "T1234";
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        GroupRoomDto groupRoomDto = getGroupRoomDto(
                roomName,
                roomOwnerId,
                enterCode
        );
        when(groupRoomService.createGroupRoom(any(CreateGroupRoomDto.class))).thenReturn(groupRoomDto);

        // when & then
        mockMvc.perform(post("/group/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(groupRoomDto))
        );
    }

    @Test
    void create_group_room_유효성검사_실패() throws Exception {

        // given
        Integer roomOwnerId = 1;
        String roomName = "";
        String enterCode = "";
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        GroupRoomDto groupRoomDto = getGroupRoomDto(
                roomName,
                roomOwnerId,
                enterCode
        );
        when(groupRoomService.createGroupRoom(any(CreateGroupRoomDto.class))).thenReturn(groupRoomDto);

        // when & then
        mockMvc.perform(post("/group/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                ;
    }

    GroupRoomDto getGroupRoomDto(String roomName, Integer roomOwnerId, String enterCode) {
        Integer roomId = 1;
        GroupRoomDetailDto groupRoomDetailDto = GroupRoomDetailDto.builder()
                .roomId(roomId)
                .roomName(roomName)
                .roomOwnerId(roomOwnerId)
                .enterCode(enterCode)
                .build();

        return GroupRoomDto.builder()
                .id(roomId)
                .type(RoomType.G)
                .groupRoomDetailDto(groupRoomDetailDto)
                .build();
    }
}