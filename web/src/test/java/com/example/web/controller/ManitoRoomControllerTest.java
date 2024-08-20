package com.example.web.controller;

import com.example.web.advice.GlobalExceptionHandler;
import com.example.web.dto.CreateManitoRoomRequest;
import com.example.web.dto.EnterManitoRoomRequest;
import com.example.web.service.ManitoRoomService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ManitoRoomControllerTest {

    @InjectMocks
    private ManitoRoomController manitoRoomController;

    @Mock
    private ManitoRoomService manitoRoomService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(manitoRoomController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    @DisplayName("마니또 채팅방을 생성합니다.")
    void create_manito_room() throws Exception {

        // given
        CreateManitoRoomRequest requestDto = new CreateManitoRoomRequest(1, 1L);

        // when & then
        mockMvc.perform(post("/manito/room")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(manitoRoomService).createManitoRooms(requestDto);
    }

    @Test
    @DisplayName("마니또 채팅방을 생성에 실패합니다. : 유효성검사 실패")
    void create_manito_room_유효성검사_실패() throws Exception {
        // given
        CreateManitoRoomRequest dto = new CreateManitoRoomRequest(1, 8L);

        // when & then
        mockMvc.perform(post("/manito/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("만료일은 최대 7일까지 지정 가능합니다."));

    }

    @Test
    @DisplayName("마니또 채팅방에 입장합니다.")
    void enter_manito_room() throws Exception {

        // given
        EnterManitoRoomRequest requestDto = new EnterManitoRoomRequest(1, 1, "nickname");

        // when & then
        mockMvc.perform(post("/manito/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(manitoRoomService).enterManitoRoom(requestDto);
    }

    @Test
    @DisplayName("마니또 채팅방 입장에 실패합니다. : 유효성검사 실패")
    void enter_manito_room_유효성검사_실패() throws Exception {
        // given
        EnterManitoRoomRequest dto = new EnterManitoRoomRequest(1, 1, "");

        // when & then
        mockMvc.perform(post("/manito/room/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("닉네임은 반드시 입력해야 합니다."));;
    }
}