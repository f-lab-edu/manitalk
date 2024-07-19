package com.example.web.repository.jpa;

import com.example.web.domain.UserRoomMission;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomMissionRepository extends BaseRepository<UserRoomMission, Integer> {
}
