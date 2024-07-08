package com.example.web.service;

import com.example.web.domain.Mission;
import com.example.web.domain.UserRoom;
import com.example.web.domain.UserRoomMission;
import com.example.web.repository.jpa.UserRoomMissionRepository;
import com.example.web.vo.MissionVo;
import com.example.web.vo.UserMissionVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManitoMissionService {

    private final UserRoomMissionRepository userRoomMissionRepository;

    private final MissionService missionService;

    private final EntityManager entityManager;

    public UserMissionVo saveUserRoomMission(Integer userRoomId) {
        MissionVo missionVo = missionService.getMission();

        UserRoom userRoom = entityManager.getReference(UserRoom.class, userRoomId);
        Mission mission = entityManager.getReference(Mission.class, missionVo.getMissionId());

        UserRoomMission userRoomMission = new UserRoomMission(userRoom, mission);
        userRoomMissionRepository.save(userRoomMission);

        // TODO: 미션 데이터 캐싱

        return new UserMissionVo(
                userRoomId,
                mission.getId(),
                mission.getKeyword()
        );
    }
}
