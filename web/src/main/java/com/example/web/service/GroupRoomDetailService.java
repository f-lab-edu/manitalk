package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.Room;
import com.example.web.repository.jpa.GroupRoomDetailRepository;
import com.example.web.vo.GroupRoomDetailVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.web.dto.CreateGroupRoomDetailParam;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupRoomDetailService {

    private final GroupRoomDetailRepository groupRoomDetailRepository;

    private final EntityManager entityManager;

    public GroupRoomDetailVo createGroupRoomDetail(CreateGroupRoomDetailParam param) {
        Room room = entityManager.getReference(Room.class, param.getRoomId());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomName(param.getRoomName());
        groupRoomDetail.setRoomOwnerId(param.getRoomOwnerId());
        groupRoomDetail.setEnterCode(param.getEnterCode());

        groupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        return new GroupRoomDetailVo(
                groupRoomDetail.getRoomName(),
                groupRoomDetail.getRoomOwnerId(),
                groupRoomDetail.getEnterCode()
        );
    }

    public boolean isRightEnterCode(Integer roomId, String enterCode) {
        Optional<GroupRoomDetail> groupRoomDetail = groupRoomDetailRepository.findById(roomId);
        return groupRoomDetail.isPresent() &&
                groupRoomDetail.get().getEnterCode().equals(enterCode);
    }

    public boolean isRoomOwner(Integer roomId, Integer userId) {
        return groupRoomDetailRepository.existsByIdAndRoomOwnerId(roomId, userId);
    }

    public void softDeleteByRoomId(Integer roomId) {
        groupRoomDetailRepository.findById(roomId).ifPresent(
                groupRoomDetail -> {
                    groupRoomDetail.setDeleted(true);
                    groupRoomDetailRepository.save(groupRoomDetail);
                }
        );
    }
}
