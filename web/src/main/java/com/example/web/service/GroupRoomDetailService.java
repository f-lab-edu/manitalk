package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.Room;
import com.example.web.repository.GroupRoomDetailRepository;
import com.example.web.vo.GroupRoomDetailVo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.web.dto.CreateGroupRoomDetailParam;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupRoomDetailService {

    private final GroupRoomDetailRepository groupRoomDetailRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public GroupRoomDetailVo createGroupRoomDetail(CreateGroupRoomDetailParam param) {
        Room room = entityManager.getReference(Room.class, param.getRoomId());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomName(param.getRoomName());
        groupRoomDetail.setRoomOwnerId(param.getRoomOwnerId());
        groupRoomDetail.setEnterCode(param.getEnterCode());

        groupRoomDetailRepository.save(groupRoomDetail);

        return createGroupRoomDetailVo(groupRoomDetail);
    }

    public boolean isRightEnterCode(Integer roomId, String enterCode) {
        Optional<GroupRoomDetail> groupRoomDetail = groupRoomDetailRepository.findById(roomId);
        return groupRoomDetail.isPresent() &&
                groupRoomDetail.get().getEnterCode().equals(enterCode);
    }

    private GroupRoomDetailVo createGroupRoomDetailVo(GroupRoomDetail groupRoomDetail) {
        return new GroupRoomDetailVo(
                groupRoomDetail.getRoomName(),
                groupRoomDetail.getRoomOwnerId(),
                groupRoomDetail.getEnterCode()
        );
    }
}
