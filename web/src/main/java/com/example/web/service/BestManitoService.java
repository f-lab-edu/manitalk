package com.example.web.service;

import com.example.web.domain.*;
import com.example.web.event.BestManitoEvent;
import com.example.web.repository.jpa.BestManitoRepository;
import com.example.web.vo.BestManitoVo;
import com.example.web.vo.UserRoomVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BestManitoService {

    private final BestManitoRepository bestManitoRepository;
    private final UserRoomService userRoomService;
    private final ManitoMissionService manitoMissionService;
    private final MessageService messageService;
    private final EntityManager entityManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void setBestManitos(Map<Integer, List<Integer>> manitoRoomIdsGroupedByGroupRoomId) {
        manitoRoomIdsGroupedByGroupRoomId.forEach(
                (groupRoomId, manitoRoomIds) -> {
                    // 그룹 채팅방의 전체 멤버를 조회합니다.
                    List<UserRoomVo> userRoomList = userRoomService.getUserRoomList(manitoRoomIds);

                    // 베스트 마니또를 선정합니다.
                    List<BestManitoVo> bestManitoVos = getBestManitoVos(userRoomList);
                    saveBestManitos(bestManitoVos);

                    // 베스트 마니또 알림 이벤트를 발행합니다.
                    applicationEventPublisher.publishEvent(new BestManitoEvent(groupRoomId, bestManitoVos));
                }
        );
    }

    public List<BestManitoVo> getBestManitoVos(List<UserRoomVo> userRoomList) {
        return userRoomList.stream()
                .map(userRoomVo -> {
                    String mission = manitoMissionService.getMissionKeyword(userRoomVo.getId());
                    Integer count = messageService.aggregateMissionCount(userRoomVo.getRoomId(), userRoomVo.getUserId(), mission);
                    return new AbstractMap.SimpleEntry<>(count, new BestManitoVo(userRoomVo.getId(), userRoomVo.getNickname()));
                })
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        TreeMap::new,
                        Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())
                ))
                .lastEntry()
                .getValue();
    }

    private void saveBestManitos(List<BestManitoVo> bestManitoVos) {
        List<BestManito> bestManitos = makeBestManitoList(bestManitoVos);
        bestManitoRepository.saveAll(bestManitos);
    }

    private List<BestManito> makeBestManitoList(List<BestManitoVo> bestManitoVos) {
        List<BestManito> bestManitos = new ArrayList<>();
        bestManitoVos.forEach(bestManitoVo -> {
            UserRoom userRoom = entityManager.getReference(UserRoom.class, bestManitoVo.getUserRoomId());
            bestManitos.add(new BestManito(userRoom));
        });
        return bestManitos;
    }
}
