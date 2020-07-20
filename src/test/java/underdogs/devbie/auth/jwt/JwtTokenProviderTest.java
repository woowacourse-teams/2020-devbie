package underdogs.devbie.auth.jwt;

import static org.assertj.core.api.Assertions.*;
import static underdogs.devbie.user.domain.UserTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.exception.InvalidAuthenticationException;
import underdogs.devbie.user.domain.User;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider("secretKey", 360000);
    }

    @DisplayName("jwt 토큰 생성")
    @Test
    void createToken() {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        String token = jwtTokenProvider.createToken(UserTokenDto.from(user));

        assertThat(token).isNotBlank();
    }

    @DisplayName("jwt 토큰 복호화")
    @Test
    void extractValidSubject() {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        String token = jwtTokenProvider.createToken(UserTokenDto.from(user));

        String extractedUserId = jwtTokenProvider.extractValidSubject(token);

        assertThat(extractedUserId).isEqualTo(String.valueOf(1L));
    }

    @DisplayName("jwt 토큰 복호화 - 유효하지 않은 토큰")
    @Test
    void extractInvalidSubject_Should_Throw_InvalidAuthenticationException() {
        String invalidToken = "token";

        assertThatThrownBy(() -> jwtTokenProvider.extractValidSubject(invalidToken))
            .isInstanceOf(InvalidAuthenticationException.class)
            .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("jwt 토큰 복호화 - 유효하지 않은 빈 토큰")
    @Test
    void extractInvalidSubject_WithEmptyString_Should_Throw_InvalidAuthenticationException() {
        String invalidToken = "";

        assertThatThrownBy(() -> jwtTokenProvider.extractValidSubject(invalidToken))
            .isInstanceOf(InvalidAuthenticationException.class)
            .hasMessage("유효하지 않은 토큰입니다.");
    }
}
