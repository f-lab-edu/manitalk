package com.example.websocket.event;

import com.example.websocket.service.MessagePublisher;
import com.example.websocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PublishEventListener {

    private final MessageService messageService;

    private final MessagePublisher messagePublisher;

    @Value("${room.channel.prefix}")
    String roomChannelPrefix;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Retryable(
            backoff = @Backoff(delay = 1000),
            recover = "saveFailedEvent"
    )
    public void handleRoomEndEvent(MessageEvent messageEvent) {
        messagePublisher.publish(
                roomChannelPrefix + messageEvent.getMessageVo().getRoomId(),
                messageEvent.getMessageVo()
        );
    }

    @Recover
    private void saveFailedEvent(MessageEvent messageEvent) {
        // 최종 발신 실패 시 메시지 저장 롤백
        messageService.deleteMessage(messageEvent.getMessageVo().getId());
    }
}
