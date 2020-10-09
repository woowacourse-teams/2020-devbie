package underdogs.devbie.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.dto.JwtTokenResponse;
import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.jwt.JwtTokenProvider;
import underdogs.devbie.auth.oauth.GithubClient;
import underdogs.devbie.user.service.UserService;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserInfoDto;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GithubClient githubClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public String fetchLoginUrl() {
        return githubClient.fetchLoginUrl();
    }

    public JwtTokenResponse createToken(String code) {
        String accessToken = githubClient.fetchAccessToken(code);

        UserInfoDto userInfoDto = githubClient.fetchUserInfo(accessToken);

        User user = userService.saveOrUpdateUser(userInfoDto);

        String token = jwtTokenProvider.createToken(UserTokenDto.from(user));
        return JwtTokenResponse.from(token);
    }
}
