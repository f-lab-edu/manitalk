package com.example.web.repository.jpa;

import com.example.web.domain.Mission;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends BaseRepository<Mission, Integer> {
    Integer count();

    @Query("SELECT m FROM Mission m ORDER BY m.id LIMIT 1 OFFSET :offset")
    Mission getRandomMission(@Param("offset") int offset);
}
