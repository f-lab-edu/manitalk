package com.example.web.repository;

import com.example.web.domain.GroupRoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRoomDetailRepository extends JpaRepository<GroupRoomDetail, Integer> {
}
