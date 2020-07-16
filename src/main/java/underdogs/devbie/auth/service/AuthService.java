package underdogs.devbie.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.oauth.GithubClient;
import underdogs.devbie.auth.service.dto.UserInfoResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GithubClient githubClient;

    public String fetchLoginUrl() {
        return githubClient.fetchLoginUrl();
    }

    public String fetchAccessToken(String code) {
        return githubClient.fetchAccessToken(code);
    }

    public UserInfoResponse fetchUserInfo(String accessToken) {
        return githubClient.fetchUserInfo(accessToken);
    }
}
