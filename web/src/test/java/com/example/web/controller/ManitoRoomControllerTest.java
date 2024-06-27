package com.example.web.controller;

import com.example.web.dto.CreateManitoRoomRequest;
import com.example.web.dto.CreateManitoRoomResponse;
import com.example.web.service.ManitoRoomService;
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

@WebMvcTest(controllers = ManitoRoomController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ManitoRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ManitoRoomService manitoRoomService;

    Integer groupRoomId = 1;
    Long expiresDays = 1L;

    @Test
    @DisplayName("마니또 채팅방을 생성합니다.")
    void create_manito_room() throws Exception {

        // given
        CreateManitoRoomRequest dto = getCreateManitoRoomsRequest();
        CreateManitoRoomResponse createManitoRoomResponse = getCreateManitoRoomsResponse();
        when(manitoRoomService.createManitoRooms(any(CreateManitoRoomRequest.class))).thenReturn(createManitoRoomResponse);

        // when & then
        mockMvc.perform(post("/manito/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(createManitoRoomResponse))
        );
    }

    @Test
    @DisplayName("마니또 채팅방을 생성에 실패합니다. : 유효성검사 실패")
    void create_manito_room_유효성검사_실패() throws Exception {
        // given
        expiresDays = 8L;
        CreateManitoRoomRequest dto = getCreateManitoRoomsRequest();

        // when & then
        mockMvc.perform(post("/manito/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    CreateManitoRoomRequest getCreateManitoRoomsRequest() {
        return new CreateManitoRoomRequest(groupRoomId, expiresDays);
    }

    CreateManitoRoomResponse getCreateManitoRoomsResponse() {
        return CreateManitoRoomResponse.builder()
                .groupRoomId(groupRoomId)
                .manitoRoomCount(1)
                .build();
    }
}