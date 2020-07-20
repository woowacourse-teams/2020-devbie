package underdogs.devbie.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.auth.dto.UserInfoResponse;

public class UserTest {

    public static final String TEST_USER_EMAIL = "underdogs@devbie.link";
    public static final String TEST_OAUTH_ID = "TEST_OAUTH_ID";

    @DisplayName("OauthId가 일치하지 않을 경우 예외발생")
    @Test
    void validateUserInfo() {
        // given
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        UserInfoResponse userInfoResponse = new UserInfoResponse("Different Oauth Id", TEST_USER_EMAIL);

        // when
        assertThatThrownBy(() -> {
            user.updateOauthInfo(userInfoResponse);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Oauth 유저 정보 업데이트")
    @Test
    void updateOauthInfo() {
        // given
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        UserInfoResponse userInfoResponse = new UserInfoResponse(TEST_OAUTH_ID, "Changed Email");

        // when
        User updatedUser = user.updateOauthInfo(userInfoResponse);

        // then
        assertThat(updatedUser.getId()).isEqualTo(1L);
        assertThat(updatedUser.getOauthId()).isEqualTo(TEST_OAUTH_ID);
        assertThat(updatedUser.getEmail()).isEqualTo("Changed Email");
    }
}