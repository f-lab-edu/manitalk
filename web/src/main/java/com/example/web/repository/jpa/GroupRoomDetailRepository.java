package com.example.web.repository.jpa;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRoomDetailRepository extends BaseRepository<GroupRoomDetail, Integer> {
    boolean existsByIdAndRoomOwnerId(Integer roomId, Integer userId);
}
