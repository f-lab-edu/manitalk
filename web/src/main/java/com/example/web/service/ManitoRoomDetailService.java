package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.ManitoRoomDetail;
import com.example.web.domain.Room;
import com.example.web.dto.CreateManitoRoomDetailsParam;
import com.example.web.repository.jpa.ManitoRoomDetailRepository;
import com.example.web.vo.ManitoRoomDetailVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManitoRoomDetailService {

    private final ManitoRoomDetailRepository manitoRoomDetailRepository;

    private final EntityManager entityManager;

    public List<Integer> createManitoRoomDetails(CreateManitoRoomDetailsParam param) {
        List<ManitoRoomDetail> manitoRoomDetails = makeRoomDetailList(param);
        manitoRoomDetails = manitoRoomDetailRepository.saveAll(manitoRoomDetails);

        return manitoRoomDetails.stream()
                .map(ManitoRoomDetail::getId)
                .collect(Collectors.toList());
    }

    private List<ManitoRoomDetail> makeRoomDetailList(CreateManitoRoomDetailsParam param) {
        List<ManitoRoomDetail> manitoRoomDetails = new ArrayList<>();

        GroupRoomDetail groupRoomDetail = entityManager.getReference(GroupRoomDetail.class, param.getGroupRoomId());
        param.getRoomIds().forEach(roomId -> {
            Room room = entityManager.getReference(Room.class, roomId);
            manitoRoomDetails.add(new ManitoRoomDetail(room, groupRoomDetail, LocalDateTime.now().plusDays(param.getExpiresDays())));
        });

        return manitoRoomDetails;
    }

    public boolean isExistsManitoRoomsInGroup(Integer groupRoomId) {
        return manitoRoomDetailRepository.existsByGroupRoomId(groupRoomId);
    }

    public Map<Integer, List<Integer>> getExpiredManitoRooms() {
        List<ManitoRoomDetailVo> manitoRoomDetails = manitoRoomDetailRepository.getExpiredManitoRoom(LocalDateTime.now());

        return manitoRoomDetails.stream()
                .collect(Collectors.groupingBy(
                        ManitoRoomDetailVo::getGroupRoomId,
                        Collectors.mapping(ManitoRoomDetailVo::getRoomId, Collectors.toList())
                ));
    }

    public void softDeleteAllByIds(List<Integer> ids) {
        List<ManitoRoomDetail> manitoRoomDetails = manitoRoomDetailRepository.findAllByIdIn(ids).stream()
                .peek(manitoRoomDetail -> manitoRoomDetail.setDeleted(true))
                .collect(Collectors.toList());
        manitoRoomDetailRepository.saveAll(manitoRoomDetails);
    }
}
