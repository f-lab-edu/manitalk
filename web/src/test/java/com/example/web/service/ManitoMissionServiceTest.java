package com.example.web.service;

import com.example.web.domain.Mission;
import com.example.web.domain.UserRoom;
import com.example.web.repository.fake.FakeUserRoomMissionRepository;
import com.example.web.vo.MissionVo;
import com.example.web.vo.UserMissionVo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManitoMissionServiceTest {

    @Mock
    private MissionService missionService;

    @Mock
    private CacheService cacheService;

    @Mock
    private EntityManager entityManager;

    private ManitoMissionService manitoMissionService;

    Integer userRoomId = 1;
    Integer missionId = 1;
    String missionKeyword = "최고";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        manitoMissionService = new ManitoMissionService(
                new FakeUserRoomMissionRepository(),
                missionService,
                cacheService,
                entityManager
        );
    }

    @Test
    @DisplayName("유저 미션을 저장합니다.")
    void save_user_room_mission() {
        // given
        MissionVo missionVo = new MissionVo(missionId, missionKeyword);
        when(missionService.getMission()).thenReturn(missionVo);

        UserRoom userRoom = new UserRoom();
        Mission mission = new Mission(missionKeyword);
        when(entityManager.getReference(eq(UserRoom.class), any())).thenReturn(userRoom);
        when(entityManager.getReference(eq(Mission.class), any())).thenReturn(mission);

        // when
        UserMissionVo userMissionVo = manitoMissionService.saveUserRoomMission(userRoomId);

        // then
        Assertions.assertEquals(userMissionVo.getUserRoomId(), userRoomId);
        Assertions.assertEquals(userMissionVo.getKeyword(), missionKeyword);
    }

}