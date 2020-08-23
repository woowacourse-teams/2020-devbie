package underdogs.devbie.auth.jwt;

import static org.assertj.core.api.Assertions.*;
import static underdogs.devbie.user.domain.UserTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.exception.AccessTokenLoadException;
import underdogs.devbie.user.domain.RoleType;
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
            .roleType(RoleType.USER)
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
            .roleType(RoleType.USER)
            .build();
        String token = jwtTokenProvider.createToken(UserTokenDto.from(user));

        Claims claims = jwtTokenProvider.extractValidSubject(token);
        String extractedUserId = claims.get("userId").toString();

        assertThat(extractedUserId).isEqualTo(String.valueOf(1L));
    }

    @DisplayName("jwt 토큰 복호화 - 유효하지 않은 토큰")
    @Test
    void extractInvalidSubject_Should_Throw_InvalidAuthenticationException() {
        String invalidToken = "token";

        assertThatThrownBy(() -> jwtTokenProvider.extractValidSubject(invalidToken))
            .isInstanceOf(AccessTokenLoadException.class)
            .hasMessage("요청자의 신분을 확인하지 못하였습니다. 원인 : 토큰을 Load하지 못하였습니다.");
    }

    @DisplayName("jwt 토큰 복호화 - 유효하지 않은 빈 토큰")
    @Test
    void extractInvalidSubject_WithEmptyString_Should_Throw_InvalidAuthenticationException() {
        String invalidToken = "";

        assertThatThrownBy(() -> jwtTokenProvider.extractValidSubject(invalidToken))
            .isInstanceOf(AccessTokenLoadException.class)
            .hasMessage("요청자의 신분을 확인하지 못하였습니다. 원인 : 토큰을 Load하지 못하였습니다.");
    }
}
