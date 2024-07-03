package com.example.web.event;

import com.example.web.domain.FailedRoomEvent;
import com.example.web.repository.FailedEventRepository;
import com.example.web.service.MessagePublisher;
import com.example.web.vo.RoomEventVo;
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

    private final FailedEventRepository failedEventRepository;

    @Value("${room.channel.prefix}")
    String channelPrefix;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    @Retryable(
            backoff = @Backoff(delay = 1000),
            recover = "saveFailedPublishEvent"
    )
    public void handleRoomEndEvent(RoomEventVo roomEventVo) {
        messagePublisher.publish(
                channelPrefix + "/" + roomEventVo.getRoomId(),
                roomEventVo
        );
    }

    @Recover
    private void saveFailedPublishEvent(RoomEventVo roomEventVo) {
        try {
            // 실패 작업 DB 저장
            FailedRoomEvent failedRoomEvent = new FailedRoomEvent(
                    roomEventVo.getRoomId(),
                    roomEventVo
            );
            failedEventRepository.save(failedRoomEvent);
        } catch (RuntimeException e) {
            // TODO: 로깅 + 알림 처리
            System.out.println("fail publish event");
        }
    }
}

