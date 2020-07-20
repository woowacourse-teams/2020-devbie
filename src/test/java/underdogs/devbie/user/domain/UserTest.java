package underdogs.devbie.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.auth.dto.UserInfoResponse;

public class UserTest {

    public static final String TEST_USER_EMAIL = "underdogs@devbie.link";
    public static final String TEST_OAUTH_ID = "TEST_OAUTH_ID";

    @DisplayName("OauthId가 일치하지 않을 경우 예외발생")
    @Test
    void validateUserInfo() {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        UserInfoResponse userInfoResponse = new UserInfoResponse("Different Oauth Id", TEST_USER_EMAIL);

        assertThatThrownBy(() -> user.updateOauthInfo(userInfoResponse))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Oauth 유저 정보 업데이트")
    @Test
    void updateOauthInfo() {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        UserInfoResponse userInfoResponse = new UserInfoResponse(TEST_OAUTH_ID, "Changed Email");

        User updatedUser = user.updateOauthInfo(userInfoResponse);

        assertAll(
            () -> assertEquals(1L, updatedUser.getId()),
            () -> assertEquals(TEST_OAUTH_ID, updatedUser.getOauthId()),
            () -> assertEquals("Changed Email", updatedUser.getEmail())
        );
    }
}