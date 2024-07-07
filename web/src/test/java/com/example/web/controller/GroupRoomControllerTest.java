package com.example.web.controller;

import com.example.web.config.TestConfig;
import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.service.GroupRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GroupRoomController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ContextConfiguration(classes = {TestConfig.class})
class GroupRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupRoomService groupRoomService;

    Integer userId = 1;

    Integer roomId = 1;

    Integer userRoomId = 1;

    Integer roomOwnerId = 1;

    String roomName = "test_room";

    String enterCode = "test_code";

    String nickname = "test";

    @Test
    @DisplayName("그룹 채팅방을 생성합니다.")
    void create_group_room() throws Exception {

        // given
        CreateGroupRoomRequest dto = new CreateGroupRoomRequest(roomOwnerId, roomName, enterCode);

        CreateGroupRoomResponse createGroupRoomResponse = getCreateGroupRoomResponse();
        when(groupRoomService.createGroupRoom(any(CreateGroupRoomRequest.class))).thenReturn(createGroupRoomResponse);

        // when & then
        mockMvc.perform(post("/group/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(createGroupRoomResponse))
        );
    }

    @Test
    @DisplayName("그룹 채팅방을 생성에 실패합니다. : 유효성검사 실패")
    void create_group_room_유효성검사_실패() throws Exception {

        // given
        roomName = "";
        enterCode = "";
        CreateGroupRoomRequest dto = new CreateGroupRoomRequest(roomOwnerId, roomName, enterCode);

        when(groupRoomService.createGroupRoom(any(CreateGroupRoomRequest.class))).thenReturn(getCreateGroupRoomResponse());

        // when & then
        mockMvc.perform(post("/group/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    CreateGroupRoomResponse getCreateGroupRoomResponse() {
        return CreateGroupRoomResponse.builder()
                .id(roomId)
                .roomType(RoomType.G)
                .roomName(roomName)
                .roomOwnerId(roomOwnerId)
                .enterCode(enterCode)
                .build();
    }

    @Test
    @DisplayName("그룹 채팅방에 입장합니다.")
    void enter_group_room() throws Exception {

        // given
        EnterGroupRoomRequest enterGroupRoomRequest = new EnterGroupRoomRequest(
                userId,
                roomId,
                roomName,
                enterCode,
                nickname
        );

        EnterRoomResponse enterRoomResponse = getEnterRoomResponse();
        when(groupRoomService.enterGroupRoom(any(EnterGroupRoomRequest.class))).thenReturn(getEnterRoomResponse());

        // when & then
        mockMvc.perform(post("/group/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enterGroupRoomRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(enterRoomResponse))
                );
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. : 유효성검사 실패")
    void enter_group_room_유효성검사_실패() throws Exception {

        // given
        EnterGroupRoomRequest enterGroupRoomRequest = new EnterGroupRoomRequest(
                userId,
                roomId,
                "",
                "",
                ""
        );

        when(groupRoomService.enterGroupRoom(any(EnterGroupRoomRequest.class))).thenReturn(getEnterRoomResponse());

        // when & then
        mockMvc.perform(post("/group/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enterGroupRoomRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    EnterRoomResponse getEnterRoomResponse() {
        return EnterRoomResponse.builder()
                .userRoomId(userRoomId)
                .nickname(nickname)
                .build();
    }

    @Test
    @DisplayName("그룹 채팅을 종료합니다.")
    void end_group_room() throws Exception {

        // given
        EndGroupRoomRequest endGroupRoomRequest = new EndGroupRoomRequest(
                roomId,
                roomOwnerId
        );

        EndRoomResponse endRoomResponse = EndRoomResponse.builder()
                .roomId(roomId)
                .build();
        when(groupRoomService.endGroupRoom(any(EndGroupRoomRequest.class))).thenReturn(endRoomResponse);

        // when & then
        mockMvc.perform(delete("/group/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endGroupRoomRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(endRoomResponse))
                );
    }
}
