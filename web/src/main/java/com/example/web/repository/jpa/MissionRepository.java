package com.example.web.repository.jpa;

import com.example.web.domain.Mission;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends BaseRepository<Mission, Integer> {
    Long count();

    Page<Mission> findAll(Pageable pageable);
}
