package underdogs.devbie.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.user.domain.UserTest.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import underdogs.devbie.auth.dto.UserInfoResponse;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.domain.UserRepository;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository);
        mockUser = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
    }

    @DisplayName("Oauth로부터 User 모델 신규 저장 및 업데이트")
    @Test
    void saveOrUpdateOauthUser() {
        // given
        UserInfoResponse userInfoResponse = new UserInfoResponse(TEST_OAUTH_ID, TEST_USER_EMAIL);
        given(userRepository.findByOauthId(any())).willReturn(Optional.of(mockUser));

        // when
        User user = userService.saveOrUpdateUser(userInfoResponse);

        // then
        verify(userRepository).findByOauthId(TEST_OAUTH_ID);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getOauthId()).isEqualTo(TEST_OAUTH_ID);
        assertThat(user.getEmail()).isEqualTo(TEST_USER_EMAIL);
    }

    @DisplayName("Id로 유저 조회")
    @Test
    void findById() {
        // given
        given(userRepository.findById(any())).willReturn(Optional.of(mockUser));

        // when
        User user = userService.findById(mockUser.getId());

        // then
        verify(userRepository).findById(eq(1L));
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getOauthId()).isEqualTo(TEST_OAUTH_ID);
        assertThat(user.getEmail()).isEqualTo(TEST_USER_EMAIL);
    }
}