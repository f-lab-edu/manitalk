package com.example.web.repository;

import com.example.web.domain.ManitoRoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManitoRoomDetailRepository extends JpaRepository<ManitoRoomDetail, Integer> {

    @Query("SELECT count(mrd) > 0 from ManitoRoomDetail mrd where mrd.groupRoomDetail.id = :groupRoomId")
    boolean existsByGroupRoomId(@Param("groupRoomId") Integer groupRoomId);
}
