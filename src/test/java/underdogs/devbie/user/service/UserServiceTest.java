package underdogs.devbie.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.user.domain.UserTest.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.auth.dto.UserInfoResponse;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.domain.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @DisplayName("Oauth로부터 User 모델 신규 저장 및 업데이트")
    @Test
    void saveOrUpdateOauthUser() {
        User mockUser =  User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        UserInfoResponse userInfoResponse = new UserInfoResponse(TEST_OAUTH_ID, TEST_USER_EMAIL);
        given(userRepository.findByOauthId(any())).willReturn(Optional.of(mockUser));

        User user = userService.saveOrUpdateUser(userInfoResponse);

        assertAll(
            () -> assertEquals(1L, user.getId()),
            () -> assertEquals(TEST_OAUTH_ID, user.getOauthId()),
            () -> assertEquals(TEST_USER_EMAIL, user.getEmail())
        );
    }

    @DisplayName("Id로 유저 조회")
    @Test
    void findById() {
        User mockUser =  User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        given(userRepository.findById(any())).willReturn(Optional.of(mockUser));

        User user = userService.findById(mockUser.getId());

        assertAll(
            () -> assertEquals(1L, user.getId()),
            () -> assertEquals(TEST_OAUTH_ID, user.getOauthId()),
            () -> assertEquals(TEST_USER_EMAIL, user.getEmail())
        );
    }
}