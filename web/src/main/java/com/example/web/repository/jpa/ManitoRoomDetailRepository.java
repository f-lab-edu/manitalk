package com.example.web.repository.jpa;

import com.example.web.domain.ManitoRoomDetail;
import com.example.web.repository.jpa.common.BaseRepository;
import com.example.web.vo.ManitoRoomDetailVo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ManitoRoomDetailRepository extends BaseRepository<ManitoRoomDetail, Integer> {

    @Query("SELECT count(mrd) > 0 from ManitoRoomDetail mrd where mrd.groupRoomDetail.id = :groupRoomId")
    boolean existsByGroupRoomId(@Param("groupRoomId") Integer groupRoomId);

    @Query("SELECT new com.example.web.vo.ManitoRoomDetailVo(mrd.id, mrd.groupRoomDetail.id) from ManitoRoomDetail mrd " +
            "where mrd.expiresAt >= :start AND mrd.expiresAt <= :end order by mrd.id LIMIT 100")
    List<ManitoRoomDetailVo> findExpiredManitoRooms(@Param("start")LocalDateTime start, @Param("end")LocalDateTime end);

    List<ManitoRoomDetail> findAllByIdIn(List<Integer> ids);
}
