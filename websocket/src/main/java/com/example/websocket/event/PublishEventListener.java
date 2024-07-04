package com.example.websocket.event;

import com.example.websocket.service.MessagePublisher;
import com.example.websocket.vo.MessageVo;
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

    private final MessagePublisher messagePublisher;

    @Value("${room.channel.prefix}")
    String channelPrefix;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Retryable(
            backoff = @Backoff(delay = 1000),
            recover = "saveFailedPublishEvent"
    )
    public void handleRoomEndEvent(MessageVo vo) {
        messagePublisher.publish(
                channelPrefix + "/" + vo.getRoomId(),
                vo
        );
    }

    @Recover
    private void saveFailedPublishEvent(MessageVo vo) {
        // TODO: 최종 실패 시 발신자에게 실패 응답
        System.out.println(vo.getRoomId() + "fail publish event");
    }
}
