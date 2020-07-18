package underdogs.devbie.auth.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.exception.InvalidAuthenticationException;
import underdogs.devbie.user.domain.User;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    private final Long userId = 1L;
    private User user;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider("secretKey", 360000);
        user = User.builder().id(userId).oauthId("oauthId").email("email").build();
    }

    @Test
    @DisplayName("jwt 토큰 생성")
    void createToken() {
        String token = jwtTokenProvider.createToken(UserTokenDto.from(user));

        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("jwt 토큰 복호화")
    void extractValidSubject() {
        String token = jwtTokenProvider.createToken(UserTokenDto.from(user));

        String extractedUserId = jwtTokenProvider.extractValidSubject(token);

        assertThat(extractedUserId).isEqualTo(String.valueOf(userId));
    }

    @Test
    @DisplayName("jwt 토큰 복호화 - 유효하지 않은 토큰")
    void extractInvalidSubject_Should_Throw_InvalidAuthenticationException() {
        String invalidToken = "token";

        assertThatThrownBy(() -> jwtTokenProvider.extractValidSubject(invalidToken))
            .isInstanceOf(InvalidAuthenticationException.class)
            .hasMessage("유효하지 않은 토큰입니다.");
    }
}
