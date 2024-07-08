package com.example.web.service;

import com.example.web.domain.Mission;
import com.example.web.repository.jpa.MissionRepository;
import com.example.web.vo.MissionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionVo getMission() {
        Long count = missionRepository.count();
        int random = (int)(Math.random() * count);

        Page<Mission> missionPage = missionRepository.findAll(PageRequest.of(random, 1));
        Mission mission = missionPage.getContent().getFirst();
        return new MissionVo(
                mission.getId(),
                mission.getKeyword()
        );
    }
}
