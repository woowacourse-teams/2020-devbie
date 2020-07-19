package underdogs.devbie.auth.oauth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.auth.dto.UserInfoResponse;

@ExtendWith(MockitoExtension.class)
class GithubClientTest {

    @Mock
    private GithubClient githubClient;

    @DisplayName("깃허브 로그인 요청하는 URL을 요청한다.")
    @Test
    void fetchLoginUrl() {
        String loginUrl = "https://github.com/login/oauth/authorize?client_id=clientId";
        when(githubClient.fetchLoginUrl()).thenReturn(loginUrl);

        String actual = githubClient.fetchLoginUrl();

        assertThat(actual).isEqualTo(loginUrl);
    }

    @DisplayName("Github의 Code를 통하여 Access토큰을 요청한다.")
    @Test
    void fetchAccessToken() {
        String code = "myCode";
        String expect = "accessToken";
        when(githubClient.fetchAccessToken(code)).thenReturn(expect);

        String accessToken = githubClient.fetchAccessToken(code);

        assertThat(accessToken).isEqualTo(expect);
    }

    @DisplayName("Access토큰을 통하여 유저 정보를 가져온다.")
    @Test
    void fetchUserInfo() {
        UserInfoResponse expect = new UserInfoResponse("1", "khb1109", "fusis1@naver.com");
        when(githubClient.fetchUserInfo(any())).thenReturn(expect);

        UserInfoResponse actual = githubClient.fetchUserInfo("accessToken");

        assertThat(actual).isEqualTo(expect);
    }
}