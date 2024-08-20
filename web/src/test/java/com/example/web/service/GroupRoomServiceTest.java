package com.example.web.service;

import com.example.web.domain.*;
import com.example.web.dto.*;
import com.example.web.enums.RoomType;
import com.example.web.exception.room.CanNotDeleteRoomException;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.DuplicatedUserRoomException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GroupRoomServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private final FakeRoomRepository roomRepository = new FakeRoomRepository();
    private final FakeGroupRoomDetailRepository groupRoomDetailRepository = new FakeGroupRoomDetailRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeUserRoomRepository userRoomRepository = new FakeUserRoomRepository();
    private final FakeManitoRoomDetailRepository manitoRoomDetailRepository = new FakeManitoRoomDetailRepository();

    private GroupRoomService groupRoomService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        RoomService roomService = new RoomService(roomRepository);
        GroupRoomDetailService groupRoomDetailService = new GroupRoomDetailService(groupRoomDetailRepository, entityManager);
        UserService userService = new UserService(userRepository);
        UserRoomService userRoomService = new UserRoomService(userRoomRepository, entityManager, applicationEventPublisher);
        ManitoRoomDetailService manitoRoomDetailService = new ManitoRoomDetailService(manitoRoomDetailRepository, entityManager);

        groupRoomService = new GroupRoomService(
                roomService,
                groupRoomDetailService,
                manitoRoomDetailService,
                userService,
                userRoomService,
                applicationEventPublisher
        );
    }

    @Test
    @DisplayName("새로운 그룹 채팅방을 생성합니다.")
    void create_group_room() {

        // given
        Integer roomOwnerId = 1;
        String roomName = "testRoom";
        String enterCode = "testCode";
        CreateGroupRoomRequest dto = new CreateGroupRoomRequest(roomOwnerId, roomName, enterCode);

        // when
        CreateGroupRoomResponse createGroupRoomResponse = groupRoomService.createGroupRoom(dto);

        // then
        Assertions.assertAll(
                () -> assertEquals(createGroupRoomResponse.getRoomType(), RoomType.G),
                () -> assertEquals(createGroupRoomResponse.getRoomOwnerId(), roomOwnerId),
                () -> assertEquals(createGroupRoomResponse.getRoomName(), roomName),
                () -> assertEquals(createGroupRoomResponse.getEnterCode(), enterCode)
        );
    }

    @Test
    @DisplayName("그룹 채팅방에 입장합니다.")
    void enter_group_room() {

        //given
        String roomName = "testRoom";
        String enterCode = "testCode";
        String nickName = "testNickName";
        Room room = roomRepository.save(new Room());
        User user = userRepository.save(new User());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(1);
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        groupRoomDetailRepository.save(groupRoomDetail);

        EnterGroupRoomRequest requestDto = new EnterGroupRoomRequest(
                user.getId(),
                room.getId(),
                roomName,
                enterCode,
                nickName
        );

        //when
        EnterRoomResponse enterRoomResponse = groupRoomService.enterGroupRoom(requestDto);

        //then
        assertEquals(enterRoomResponse.getNickname(), nickName);
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 채팅방 존재하지 않음")
    void enter_group_room_채팅방_존재하지_않음() {
        String roomName = "testRoom";
        String enterCode = "testCode";
        String nickName = "testNickName";
        EnterGroupRoomRequest requestDto = new EnterGroupRoomRequest(
                1,
                1,
                roomName,
                enterCode,
                nickName
        );

        //when & then
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            groupRoomService.enterGroupRoom(requestDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 입장 코드 틀림")
    void enter_group_room_입장_코드_틀림() {
        //given
        String roomName = "testRoom";
        String enterCode = "testCode";
        String nickName = "testNickName";
        Room room = roomRepository.save(new Room());
        User user = userRepository.save(new User());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(1);
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        groupRoomDetailRepository.save(groupRoomDetail);

        EnterGroupRoomRequest requestDto = new EnterGroupRoomRequest(
                user.getId(),
                room.getId(),
                roomName,
                "wrongCode",
                nickName
        );

        //when & then
        Assertions.assertThrows(CanNotEnterRoomException.class, () -> {
            groupRoomService.enterGroupRoom(requestDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 사용자를 찾을 수 없음")
    void enter_group_room_사용자를_찾을_수_없음() {
        //given
        String roomName = "testRoom";
        String enterCode = "testCode";
        String nickName = "testNickName";
        Room room = roomRepository.save(new Room());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(1);
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        groupRoomDetailRepository.save(groupRoomDetail);

        EnterGroupRoomRequest requestDto = new EnterGroupRoomRequest(
                1,
                room.getId(),
                roomName,
                enterCode,
                nickName
        );

        //when & then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            groupRoomService.enterGroupRoom(requestDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅방 입장에 실패합니다. - 이미 입장한 사용자")
    void enter_group_room_이미_입장한_사용자() {
        //given
        String roomName = "testRoom";
        String enterCode = "testCode";
        String nickName = "testNickName";
        Room room = roomRepository.save(new Room());
        User user = userRepository.save(new User());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(1);
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        groupRoomDetailRepository.save(groupRoomDetail);

        userRoomRepository.save(new UserRoom(user, room, nickName));

        EnterGroupRoomRequest requestDto = new EnterGroupRoomRequest(
                1,
                room.getId(),
                roomName,
                enterCode,
                nickName
        );

        //when & then
        Assertions.assertThrows(DuplicatedUserRoomException.class, () -> {
            groupRoomService.enterGroupRoom(requestDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅을 종료합니다.")
    void end_group_room() {

        //given
        String roomName = "testRoom";
        String enterCode = "testCode";
        Room room = roomRepository.save(new Room());
        User user = userRepository.save(new User());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(user.getId());
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        groupRoomDetailRepository.save(groupRoomDetail);

        EndGroupRoomRequest requestDto = new EndGroupRoomRequest(room.getId(), user.getId());

        //when
        EndRoomResponse endRoomResponse = groupRoomService.endGroupRoom(requestDto);

        //then
        Assertions.assertAll(
                () -> assertEquals(endRoomResponse.getRoomId(), room.getId()),
                () -> assertTrue(roomRepository.findById(room.getId()).isEmpty()),
                () -> assertTrue(groupRoomDetailRepository.findById(room.getId()).isEmpty()),
                () -> assertTrue(userRoomRepository.findByRoomId(room.getId()).isEmpty())
        );
    }

    @Test
    @DisplayName("그룹 채팅 종료에 실패합니다_채팅방이 존재하지 않음")
    void end_group_room_채팅방이_존재하지_않음() {

        //given
        EndGroupRoomRequest requestDto = new EndGroupRoomRequest(1, 1);

        //when & then
        Assertions.assertThrows(RoomNotFoundException.class, () -> {
            groupRoomService.endGroupRoom(requestDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅 종료에 실패합니다_종료 권한이 없음")
    void end_group_room_종료_권한이_없음() {

        //given
        String roomName = "testRoom";
        String enterCode = "testCode";
        Room room = roomRepository.save(new Room());
        User user = userRepository.save(new User());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(user.getId());
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        groupRoomDetailRepository.save(groupRoomDetail);

        EndGroupRoomRequest requestDto = new EndGroupRoomRequest(room.getId(), 100);

        //when & then
        Assertions.assertThrows(CanNotDeleteRoomException.class, () -> {
            groupRoomService.endGroupRoom(requestDto);
        });
    }

    @Test
    @DisplayName("그룹 채팅 종료에 실패합니다_마니또 채팅 진행중")
    void end_group_room_마니또_채팅_진행중() {

        //given
        String roomName = "testRoom";
        String enterCode = "testCode";
        Room room = roomRepository.save(new Room());
        User user = userRepository.save(new User());

        GroupRoomDetail groupRoomDetail = new GroupRoomDetail(room);
        groupRoomDetail.setRoomOwnerId(user.getId());
        groupRoomDetail.setRoomName(roomName);
        groupRoomDetail.setEnterCode(enterCode);
        groupRoomDetail = groupRoomDetailRepository.save(groupRoomDetail);

        Room manitoRoom = roomRepository.save(new Room(RoomType.M));
        ManitoRoomDetail manitoRoomDetail = new ManitoRoomDetail(manitoRoom, groupRoomDetail, LocalDateTime.now().plusDays(1L));
        manitoRoomDetailRepository.save(manitoRoomDetail);

        EndGroupRoomRequest requestDto = new EndGroupRoomRequest(room.getId(), user.getId());

        //when & then
        Assertions.assertThrows(CanNotDeleteRoomException.class, () -> {
            groupRoomService.endGroupRoom(requestDto);
        });
    }
}