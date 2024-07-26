package com.example.web.batch;

import com.example.web.event.EndRoomEvent;
import com.example.web.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class EndManitoRoomTask {

    private final ManitoRoomDetailService manitoRoomDetailService;
    private final RoomService roomService;
    private final UserRoomService userRoomService;
    private final BestManitoService bestManitoService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void endManitoRoom() {

        // 종료 대상 마니또 채팅방을 조회합니다.
        Map<Integer, List<Integer>> manitoRoomIdsGroupedByGroupRoomId = manitoRoomDetailService.getExpiredManitoRooms();
        if (manitoRoomIdsGroupedByGroupRoomId.isEmpty()) {
            return;
        }

        // 베스트 마니또를 정하고 저장합니다.
        bestManitoService.setBestManitos(manitoRoomIdsGroupedByGroupRoomId);
        List<Integer> roomIds = manitoRoomIdsGroupedByGroupRoomId.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // 채팅방 데이터를 삭제합니다.
        userRoomService.softDeleteAllByRoomIds(roomIds);
        manitoRoomDetailService.softDeleteAllByIds(roomIds);
        roomService.softDeleteAllByIds(roomIds);

        // 마니또 채팅 종료 이벤트를 발행합니다.
        roomIds.forEach(
                roomId -> {
                    applicationEventPublisher.publishEvent(new EndRoomEvent(roomId));
                }
        );
    }
}
