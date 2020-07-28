package underdogs.devbie.user.service;

import static org.assertj.core.api.Assertions.*;
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
import underdogs.devbie.user.dto.UserCreateRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
    }

    @DisplayName("Oauth로부터 User 모델 신규 저장 및 업데이트")
    @Test
    void saveOrUpdateOauthUser() {
        UserInfoResponse userInfoResponse = new UserInfoResponse(TEST_OAUTH_ID, TEST_USER_EMAIL);
        given(userRepository.findByOauthId(any())).willReturn(Optional.of(user));

        User savedUser = userService.saveOrUpdateUser(userInfoResponse);

        assertAll(
            () -> assertThat(savedUser.getId()).isEqualTo(1L),
            () -> assertThat(savedUser.getOauthId()).isEqualTo(TEST_OAUTH_ID),
            () -> assertThat(savedUser.getEmail()).isEqualTo(TEST_USER_EMAIL)
        );
    }

    @DisplayName("Id로 유저 조회")
    @Test
    void findById() {
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        User findUser = userService.findById(1L);

        assertAll(
            () -> assertThat(findUser.getId()).isEqualTo(1L),
            () -> assertThat(findUser.getOauthId()).isEqualTo(TEST_OAUTH_ID),
            () -> assertThat(findUser.getEmail()).isEqualTo(TEST_USER_EMAIL)
        );
    }

    @DisplayName("유저 생성 - OAuthId 없이")
    @Test
    void saveUserWithoutOAuthId() {
        UserCreateRequest request = UserCreateRequest.builder()
            .email("atdd@atdd.com")
            .build();
        given(userRepository.save(any(User.class))).willReturn(user);

        Long userId = userService.saveWithoutOAuthId(request);

        assertThat(userId).isEqualTo(user.getId());
    }
}