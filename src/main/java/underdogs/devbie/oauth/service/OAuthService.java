package underdogs.devbie.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import underdogs.devbie.oauth.service.dto.UserInfoResponse;
import underdogs.devbie.oauth.util.GithubClient;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final GithubClient githubClient;

    public String fetchLoginUrl() {
        return githubClient.fetchLoginUrl();
    }

    public String fetchAccessToken(@RequestParam("code") String code) {
        return githubClient.fetchAccessToken(code);
    }

    public UserInfoResponse fetchUserInfo(String accessToken) {
        return githubClient.fetchUserInfo(accessToken);
    }
}
