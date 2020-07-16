package underdogs.devbie.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.jwt.JwtTokenProvider;
import underdogs.devbie.auth.oauth.GithubClient;
import underdogs.devbie.auth.service.dto.UserInfoResponse;
import underdogs.devbie.auth.service.dto.UserTokenDto;
import underdogs.devbie.user.domain.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GithubClient githubClient;
    private final JwtTokenProvider jwtTokenProvider;

    public String fetchLoginUrl() {
        return githubClient.fetchLoginUrl();
    }

    public String fetchAccessToken(String code) {
        return githubClient.fetchAccessToken(code);
    }

    public UserInfoResponse fetchUserInfo(String accessToken) {
        return githubClient.fetchUserInfo(accessToken);
    }

    public String createToken(User user) {
        return jwtTokenProvider.createToken(UserTokenDto.from(user));
    }
}
