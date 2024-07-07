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
public class PublishEventListener {

    private final MessagePublisher messagePublisher;

    @Value("${room.channel.prefix}")
    String channelPrefix;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    @Retryable(
            backoff = @Backoff(delay = 1000),
            recover = "failRoomEvent"
    )
    public void handleRoomEvent(RoomEvent roomEvent) {
        messagePublisher.publish(
                channelPrefix + "/" + roomEvent.getRoomId(),
                roomEvent
        );
    }

    @Recover
    private void failRoomEvent(RoomEvent roomEvent) {
        // TODO: 최종 실패 로깅 처리
        System.out.println(roomEvent.roomId + " - 삭제 이벤트 전송 실패");
    }
}
