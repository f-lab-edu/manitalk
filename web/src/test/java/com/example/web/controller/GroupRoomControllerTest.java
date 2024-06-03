package com.example.web.controller;

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
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        GroupRoomDto groupRoomDto = getGroupRoomDto();
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
    @DisplayName("그룹 채팅방을 생성에 실패합니다. : 유효성검사 실패")
    void create_group_room_유효성검사_실패() throws Exception {

        // given
        roomName = "";
        enterCode = "";
        CreateGroupRoomDto dto = new CreateGroupRoomDto(roomOwnerId, roomName, enterCode);

        when(groupRoomService.createGroupRoom(any(CreateGroupRoomDto.class))).thenReturn(getGroupRoomDto());

        // when & then
        mockMvc.perform(post("/group/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    GroupRoomDto getGroupRoomDto() {
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

    @Test
    @DisplayName("그룹 채팅방에 입장합니다.")
    void enter_group_room() throws Exception {

        // given
        EnterGroupRoomDto enterGroupRoomDto = new EnterGroupRoomDto(
                userId,
                roomId,
                roomName,
                enterCode,
                nickname
        );

        UserRoomDto userRoomDto = getUserRoomDto();
        when(groupRoomService.enterGroupRoom(any(EnterGroupRoomDto.class))).thenReturn(getUserRoomDto());

        // when & then
        mockMvc.perform(post("/group/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enterGroupRoomDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userRoomDto))
                );
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. : 유효성검사 실패")
    void enter_group_room_유효성검사_실패() throws Exception {

        // given
        EnterGroupRoomDto enterGroupRoomDto = new EnterGroupRoomDto(
                userId,
                roomId,
                "",
                "",
                ""
        );

        when(groupRoomService.enterGroupRoom(any(EnterGroupRoomDto.class))).thenReturn(getUserRoomDto());

        // when & then
        mockMvc.perform(post("/group/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enterGroupRoomDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    UserRoomDto getUserRoomDto() {
        return UserRoomDto.builder()
                .userRoomId(userRoomId)
                .userId(userId)
                .roomId(roomId)
                .roomType(RoomType.G)
                .nickname(nickname)
                .build();
    }
}