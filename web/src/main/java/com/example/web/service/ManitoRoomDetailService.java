package com.example.web.service;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.domain.ManitoRoomDetail;
import com.example.web.domain.Room;
import com.example.web.dto.CreateManitoRoomDetailsParam;
import com.example.web.repository.ManitoRoomDetailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManitoRoomDetailService {

    private final ManitoRoomDetailRepository manitoRoomDetailRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Integer> createManitoRoomDetails(CreateManitoRoomDetailsParam param) {
        List<ManitoRoomDetail> manitoRoomDetails = makeRoomDetailList(param);
        manitoRoomDetailRepository.saveAll(manitoRoomDetails);

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
}
