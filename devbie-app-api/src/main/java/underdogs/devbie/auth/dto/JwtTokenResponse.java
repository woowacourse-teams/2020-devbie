package underdogs.devbie.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class JwtTokenResponse {

    private final String token;

    public static JwtTokenResponse from(String token) {
        return new JwtTokenResponse(token);
    }
}
