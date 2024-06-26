package com.example.web.service;

import com.example.web.domain.Message;
import com.example.web.dto.SendMessageRequest;
import com.example.web.dto.SendMessageResponse;
import com.example.web.exception.room.CanNotSendMessageException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
import com.example.web.repository.MessageRepository;
import com.example.web.vo.MessageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${room.channel.prefix}")
    String channelPrefix;

    private final MessagePublisher messagePublisher;

    private final RoomService roomService;

    private final UserService userService;

    private final UserRoomService userRoomService;

    private final MessageRepository messageRepository;

    public SendMessageResponse sendMessage(SendMessageRequest dto) {
        if (!roomService.isExistsRoom(dto.getRoomId())) {
            throw new RoomNotFoundException("존재하지 않는 채팅방입니다.");
        }

        if (!userService.isExistsUser(dto.getUserId())) {
            throw new UserNotFoundException("존재하지 않는 사용자입니다.");
        }

        if (!userRoomService.isExistsUserRoom(dto.getUserId(), dto.getRoomId())) {
            throw new CanNotSendMessageException("채팅방의 멤버가 아니면 메시지를 전송할 수 없습니다.");
        }

        MessageVo messageVo = saveMessage(dto);

        publishMessage(messageVo);

        return SendMessageResponse.builder()
                .messageId(messageVo.getId())
                .build();
    }

    public MessageVo saveMessage(SendMessageRequest dto) {
        Message message = new Message(
                dto.getRoomId(),
                dto.getUserId(),
                dto.getMessageType(),
                dto.getContent()
        );
        message = messageRepository.save(message);

        return new MessageVo(
                message.getId(),
                message.getRoomId(),
                message.getUserId(),
                message.getType(),
                message.getContent()
        );
    }

    public void publishMessage(MessageVo messageVo) {
        try {
            messagePublisher.publish(
                    channelPrefix + "/" + messageVo.getRoomId(),
                    messageVo
            );
        } catch (RuntimeException e) {
            // TODO: 전송 실패 로깅
            System.out.println(messageVo.getId() + " : 메시지 전송에 실패하였습니다.");
        }
    }
}
