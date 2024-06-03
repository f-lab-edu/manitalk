package com.example.web.service;

import com.example.web.domain.User;
import com.example.web.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user;

    Integer userId = 1;

    @BeforeEach
    public void setUp() throws Exception {
        user = new User();
        setId(user, userId);
    }

    @Test
    @DisplayName("사용자 ID로 사용자를 조회합니다.")
    void find_by_user_id() {

        // given
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // when
        Optional<User> findUser = userService.findByUserId(userId);

        // then
        assertTrue(findUser.isPresent());
    }

    private void setId(User room, Integer id) throws Exception {
        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(room, id);
    }
}