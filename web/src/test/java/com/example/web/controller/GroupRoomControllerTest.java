package com.example.web.controller;

import com.example.web.advice.GlobalExceptionHandler;
import com.example.web.dto.*;
import com.example.web.service.GroupRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GroupRoomControllerTest {

    @InjectMocks
    private GroupRoomController groupRoomController;

    @Mock
    private GroupRoomService groupRoomService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupRoomController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    @DisplayName("그룹 채팅방을 생성합니다.")
    void create_group_room() throws Exception {

        // given
        CreateGroupRoomRequest requestDto = new CreateGroupRoomRequest(1, "testRoom", "testCode");

        // when & then
        mockMvc.perform(post("/group/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(groupRoomService).createGroupRoom(requestDto);
    }

    @Test
    @DisplayName("그룹 채팅방 생성에 실패합니다. : 유효성검사 실패")
    void create_group_room_유효성검사_실패() throws Exception {

        // given
        CreateGroupRoomRequest requestDto = new CreateGroupRoomRequest(1, "testRoom", "");

        // when & then
        mockMvc.perform(post("/group/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("채팅방 입장 코드는 반드시 입력해야 합니다."))
        ;
    }

    @Test
    @DisplayName("그룹 채팅방에 입장합니다.")
    void enter_group_room() throws Exception {

        // given
        EnterGroupRoomRequest requestDto = new EnterGroupRoomRequest(
                1,
                1,
                "testRoom",
                "testCode",
                "nickname"
        );

        // when & then
        mockMvc.perform(post("/group/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(groupRoomService).enterGroupRoom(requestDto);
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. : 유효성검사 실패")
    void enter_group_room_유효성검사_실패() throws Exception {

        // given
        EnterGroupRoomRequest requestDto = new EnterGroupRoomRequest(
                1,
                1,
                "testRoom",
                "",
                "nickname"
        );

        // when & then
        mockMvc.perform(post("/group/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("입장 코드는 반드시 입력해야 합니다."));
    }

    @Test
    @DisplayName("그룹 채팅을 종료합니다.")
    void end_group_room() throws Exception {

        // given
        EndGroupRoomRequest requestDto = new EndGroupRoomRequest(1, 1);

        // when & then
        mockMvc.perform(delete("/group/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(groupRoomService).endGroupRoom(requestDto);
    }
}
