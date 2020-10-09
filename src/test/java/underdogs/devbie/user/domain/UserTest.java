package underdogs.devbie.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.user.dto.UserInfoDto;

public class UserTest {

    public static final String TEST_NAME = "test_name";
    public static final String TEST_USER_EMAIL = "underdogs@devbie.link";
    public static final String TEST_OAUTH_ID = "TEST_OAUTH_ID";
    public static final String TEST_GITHUB_ID = "underdogs";
    public static final String TEST_AVATAR_URL = "TEST_AVATAR_URL";

    @DisplayName("OauthId가 일치하지 않을 경우 예외발생")
    @Test
    void validateUserInfo() {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        UserInfoDto userInfoDto = new UserInfoDto("Different Oauth Id", TEST_USER_EMAIL, TEST_GITHUB_ID,
            TEST_AVATAR_URL);

        assertThatThrownBy(() -> user.updateOauthInfo(userInfoDto))
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
        UserInfoDto userInfoDto = new UserInfoDto(TEST_OAUTH_ID, "Changed Email", TEST_GITHUB_ID, TEST_AVATAR_URL);

        User updatedUser = user.updateOauthInfo(userInfoDto);

        assertAll(
            () -> assertEquals(1L, updatedUser.getId()),
            () -> assertEquals(TEST_OAUTH_ID, updatedUser.getOauthId()),
            () -> assertEquals("Changed Email", updatedUser.getEmail())
        );
    }
}
