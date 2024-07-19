package com.example.web.service;

import com.example.web.domain.Mission;
import com.example.web.repository.jpa.MissionRepository;
import com.example.web.vo.MissionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    @Cacheable(cacheNames = "meta", key = "'missionCount'")
    public Integer getMissionCount() {
        return missionRepository.count();
    }

    public MissionVo getMission(int offset) {
        Mission mission = missionRepository.getRandomMission(offset);
        return new MissionVo(
                mission.getId(),
                mission.getKeyword()
        );
    }
}
