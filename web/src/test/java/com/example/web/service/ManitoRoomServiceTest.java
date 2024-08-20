package com.example.web.service;

import com.example.web.domain.*;
import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedRoomException;
import com.example.web.repository.fake.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ManitoRoomServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CacheService cacheService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private final FakeRoomRepository roomRepository = new FakeRoomRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeUserRoomRepository userRoomRepository = new FakeUserRoomRepository();
    private final FakeManitoRoomDetailRepository manitoRoomDetailRepository = new FakeManitoRoomDetailRepository();
    private final FakeUserRoomMissionRepository userRoomMissionRepository = new FakeUserRoomMissionRepository();
    private final FakeMissionRepository missionRepository = new FakeMissionRepository();
    private final FakeGroupRoomDetailRepository groupRoomDetailRepository = new FakeGroupRoomDetailRepository();

    private ManitoRoomService manitoRoomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        RoomService roomService = new RoomService(roomRepository);
        ManitoRoomDetailService manitoRoomDetailService = new ManitoRoomDetailService(manitoRoomDetailRepository, entityManager);
        UserRoomService userRoomService = new UserRoomService(userRoomRepository, entityManager, applicationEventPublisher);
        MissionService missionService = new MissionService(missionRepository);
        ManitoMissionService manitoMissionService = new ManitoMissionService(userRoomMissionRepository, missionService, cacheService, entityManager);

        manitoRoomService = new ManitoRoomService(
                roomService,
                manitoRoomDetailService,
                userRoomService,
                manitoMissionService,
                applicationEventPublisher
        );

    }

    @Test
    @DisplayName("4명이 참여하고 있는 그룹 채팅에서 마니또 채팅방을 생성합니다.")
    void create_manito_rooms() {

        // given
        Long expiresDays = 1L;
        int count = 4;

        Room room = roomRepository.save(new Room());
        for (int i=0; i < count; i++) {
            User user = userRepository.save(new User());
            userRoomRepository.save(new UserRoom(user, room, "nickname" + i));
        }

        Mission mission = new Mission("감동");
        missionRepository.save(mission);

        CreateManitoRoomRequest requestDto = new CreateManitoRoomRequest(
                room.getId(),
                expiresDays
        );

        // when
        CreateManitoRoomResponse createManitoRoomResponse = manitoRoomService.createManitoRooms(requestDto);

        // then
        Assertions.assertAll(
                () -> assertEquals(createManitoRoomResponse.getGroupRoomId(), room.getId()),
                () -> assertEquals(createManitoRoomResponse.getManitoRoomCount(), count/2 + count%2)
        );
    }

    @Test
    @DisplayName("마니또 채팅방 생성에 실패합니다. - 이미 진행중인 마니또 채팅 존재")
    void create_manito_rooms_이미_진행중인_마니또_채팅_존재() {

        // given
        long expiresDays = 1L;

        Room room = roomRepository.save(new Room());
        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(1);
        groupRoomDetail.setRoomName("testRoom");
        groupRoomDetail.setEnterCode("testCode");
        groupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        Room manitoRoom = roomRepository.save(new Room(RoomType.M));
        ManitoRoomDetail manitoRoomDetail = new ManitoRoomDetail(manitoRoom, groupRoomDetail, LocalDateTime.now().plusDays(expiresDays));
        manitoRoomDetailRepository.save(manitoRoomDetail);

        CreateManitoRoomRequest requestDto = new CreateManitoRoomRequest(
                room.getId(),
                expiresDays
        );

        //when & then
        Assertions.assertThrows(DuplicatedRoomException.class, () -> {
            manitoRoomService.createManitoRooms(requestDto);
        });
    }

    @Test
    @DisplayName("마니또 채팅방에 처음 입장합니다.")
    void enter_manito_room() {

        // given
        long expiresDays = 1L;
        String nickname = "nickname";

        Room room = roomRepository.save(new Room());
        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(1);
        groupRoomDetail.setRoomName("testRoom");
        groupRoomDetail.setEnterCode("testCode");
        groupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        Room manitoRoom = roomRepository.save(new Room(RoomType.M));
        ManitoRoomDetail manitoRoomDetail = new ManitoRoomDetail(manitoRoom, groupRoomDetail, LocalDateTime.now().plusDays(expiresDays));
        manitoRoomDetailRepository.save(manitoRoomDetail);

        User user = userRepository.save(new User());
        UserRoom userRoom = userRoomRepository.save(new UserRoom(user, manitoRoom, ""));

        EnterManitoRoomRequest requestDto = new EnterManitoRoomRequest(
                user.getId(),
                manitoRoom.getId(),
                nickname
        );

        // when
        EnterManitoRoomResponse enterManitoRoomResponse = manitoRoomService.enterManitoRoom(requestDto);

        // then
        Assertions.assertAll(
                () -> assertTrue(userRoomRepository.findById(userRoom.getId()).isPresent()),
                () -> assertEquals(userRoom.getId(), enterManitoRoomResponse.getUserRoomId()),
                () -> assertEquals(userRoomRepository.findById(userRoom.getId()).get().getNickname(), nickname)
        );
    }

    @Test
    @DisplayName("마니또 채팅방 처음 입장에 실패합니다. - 채팅방의 멤버가 아님")
    void enter_manito_room_채팅방의_멤버가_아님() {

        // given
        Room room = roomRepository.save(new Room());
        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(1);
        groupRoomDetail.setRoomName("testRoom");
        groupRoomDetail.setEnterCode("testCode");
        groupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        Room manitoRoom = roomRepository.save(new Room(RoomType.M));
        ManitoRoomDetail manitoRoomDetail = new ManitoRoomDetail(manitoRoom, groupRoomDetail, LocalDateTime.now().plusDays(1L));
        manitoRoomDetailRepository.save(manitoRoomDetail);

        User user = userRepository.save(new User());

        EnterManitoRoomRequest requestDto = new EnterManitoRoomRequest(
                user.getId(),
                manitoRoom.getId(),
                "nickname"
        );

        //when & then
        Assertions.assertThrows(CanNotEnterRoomException.class, () -> {
            manitoRoomService.enterManitoRoom(requestDto);
        });
    }
}