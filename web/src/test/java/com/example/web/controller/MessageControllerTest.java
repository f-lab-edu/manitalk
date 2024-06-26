package com.example.web.controller;

import com.example.web.config.TestConfig;
import com.example.web.dto.*;
import com.example.web.enums.MessageType;
import com.example.web.service.MessageService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MessageController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ContextConfiguration(classes = {TestConfig.class})
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageService messageService;

    Integer userId = 1;

    Integer roomId = 1;

    MessageType messageType = MessageType.T;

    String content = "Hi";

    @Test
    @DisplayName("메시지를 발신합니다.")
    void send_message() throws Exception {

        // given
        SendMessageRequest dto = new SendMessageRequest(roomId, userId, messageType, content);

        SendMessageResponse sendMessageResponse = SendMessageResponse.builder()
                .messageId("message-1")
                .build();
        when(messageService.sendMessage(any(SendMessageRequest.class))).thenReturn(sendMessageResponse);

        // when & then
        mockMvc.perform(post("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sendMessageResponse))
        );
    }

    @Test
    @DisplayName("메시지를 발신합니다. : 유효성검사 실패")
    void send_message_유효성검사_실패() throws Exception {

        // given
        content = "";
        SendMessageRequest dto = new SendMessageRequest(roomId, userId, messageType, content);

        // when & then
        mockMvc.perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
