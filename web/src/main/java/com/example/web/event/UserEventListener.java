package com.example.web.event;

import com.example.web.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final MessagePublisher messagePublisher;

    @Value("${user.channel.prefix}")
    String channelPrefix;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    @Retryable(
            backoff = @Backoff(delay = 1000),
            recover = "failRoomEvent"
    )
    public void handleRoomEvent(UserEvent userEvent) {
        messagePublisher.publish(
                channelPrefix + userEvent.getUserId(),
                userEvent
        );
    }

    @Recover
    private void failRoomEvent(UserEvent userEvent) {
        System.out.println(userEvent.getUserId() + " - " + userEvent.getEventType() +" 이벤트 전송 실패");
    }
}
