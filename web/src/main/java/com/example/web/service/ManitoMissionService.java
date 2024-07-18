package com.example.web.service;

import com.example.web.domain.*;
import com.example.web.repository.jpa.UserRoomMissionRepository;
import com.example.web.vo.MissionVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ManitoMissionService {

    @Value("${mission.cache.name}")
    private String missionCacheName;

    private final UserRoomMissionRepository userRoomMissionRepository;
    private final MissionService missionService;
    private final CacheService cacheService;
    private final EntityManager entityManager;

    public void createUserRoomMissions(List<Integer> userRoomIds) {
        List<UserRoomMission> userRoomMissions = makeUserRoomMissionList(userRoomIds);
        userRoomMissionRepository.saveAll(userRoomMissions);
    }

    private List<UserRoomMission> makeUserRoomMissionList(List<Integer> userRoomIds) {
        List<UserRoomMission> userRoomMissions = new ArrayList<>();
        Integer missionCount = missionService.getMissionCount();

        userRoomIds.forEach(userRoomId -> {
            // 지정할 미션을 조회한다.
            int range = (int)(Math.random() * missionCount);
            MissionVo missionVo = missionService.getMission(range);

            // 유저-미션 데이터를 생성하여 리스트에 저장한다.
            Mission mission = entityManager.getReference(Mission.class, missionVo.getMissionId());
            UserRoom userRoom = entityManager.getReference(UserRoom.class, userRoomId);
            userRoomMissions.add(new UserRoomMission(userRoom, mission));

            // 미션을 캐시로 저장한다.
            saveUserMissionCache(userRoomId, missionVo.getKeyword());
        });

        return userRoomMissions;
    }

    private void saveUserMissionCache(Integer userRoomId, String keyword) {
        try {
            cacheService.putCache(missionCacheName, userRoomId.toString(), keyword);
        } catch (RuntimeException e) {
            // TODO: 로깅 추가
            System.out.println(e.getMessage());
        }
    }

    public String getMissionKeyword(Integer userRoomId) {
        String mission = String.valueOf(cacheService.getCache(missionCacheName, userRoomId.toString()));

        if (mission == null) {
            mission = Objects.requireNonNull(userRoomMissionRepository.findById(userRoomId).orElse(null)).getMission().getKeyword();
        }
        return mission;
    }
}
