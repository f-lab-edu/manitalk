package com.example.web.repository.jpa;

import com.example.web.domain.ManitoRoomDetail;
import com.example.web.repository.jpa.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManitoRoomDetailRepository extends BaseRepository<ManitoRoomDetail, Integer> {

    @Query("SELECT count(mrd) > 0 from ManitoRoomDetail mrd where mrd.groupRoomDetail.id = :groupRoomId")
    boolean existsByGroupRoomId(@Param("groupRoomId") Integer groupRoomId);
}
