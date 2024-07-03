package com.example.web.service;

import com.example.web.domain.Message;
import com.example.web.dto.*;
import com.example.web.enums.MessageType;
import com.example.web.exception.room.CanNotSendMessageException;
import com.example.web.exception.room.FailSendMessageException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
import com.example.web.repository.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @Mock
    private UserRoomService userRoomService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    Integer userId = 1;

    Integer roomId = 1;

    String messageId = "message-1";

    MessageType messageType = MessageType.T;

    String content = "Hi";

    SendMessageRequest sendMessageRequest;

    Message message;

    @BeforeEach
    public void setUp() throws Exception {
        message = new Message(
                roomId,
                userId,
                messageType,
                content
        );
        setMessageId(message, messageId);

        sendMessageRequest = new SendMessageRequest(roomId, userId, messageType, content);
    }

    @Test
    @DisplayName("메시지를 발신합니다.")
    void send_message() {

        // given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(userService.isExistsUser(any())).thenReturn(true);
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(true);
        when(messageRepository.save(any())).thenReturn(message);

        // when
        SendMessageResponse sendMessageResponse = messageService.sendMessage(sendMessageRequest);

        // then
        Assertions.assertEquals(sendMessageResponse.getMessageId(), messageId);
    }

    @Test
    @DisplayName("메시지 발신에 실패합니다. - 존재하지 않는 채팅방")
    void send_message_존재하지_않는_채팅방() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            messageService.sendMessage(sendMessageRequest);
        });
    }

    @Test
    @DisplayName("메시지 발신에 실패합니다. - 존재하지 않는 사용자")
    void send_message_존재하지_않는_사용자() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(userService.isExistsUser(any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            messageService.sendMessage(sendMessageRequest);
        });
    }

    @Test
    @DisplayName("메시지 발신에 실패합니다. - 채팅방의 멤버가 아님")
    void send_message_채팅방의_멤버가_아님() {

        //given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(userService.isExistsUser(any())).thenReturn(true);
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(false);

        //when & then
        Assertions.assertThrows(CanNotSendMessageException.class, () -> {
            messageService.sendMessage(sendMessageRequest);
        });
    }

    @Test
    @DisplayName("메시지 발신에 실패합니다. - 메시지 저장 실패")
    void send_message_메시지_저장_실패() {

        // given
        when(roomService.isExistsRoom(any())).thenReturn(true);
        when(userService.isExistsUser(any())).thenReturn(true);
        when(userRoomService.isExistsUserRoom(any(), any())).thenReturn(true);
        when(messageRepository.save(any())).thenReturn(null);

        //when & then
        Assertions.assertThrows(FailSendMessageException.class, () -> {
            messageService.sendMessage(sendMessageRequest);
        });
    }

    private void setMessageId(Message message, String id) throws Exception {
        Field idField = Message.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(message, id);
    }
}
