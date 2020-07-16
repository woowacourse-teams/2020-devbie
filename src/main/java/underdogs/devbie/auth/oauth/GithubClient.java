package underdogs.devbie.auth.oauth;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Getter;
import reactor.core.publisher.Mono;
import underdogs.devbie.auth.service.dto.AccessTokenRequest;
import underdogs.devbie.auth.service.dto.AccessTokenResponse;
import underdogs.devbie.auth.service.dto.UserInfoResponse;

@Getter
@Component
public class GithubClient {

    private static final String GITHUB_LOGIN_URL_PREFIX = "https://github.com/login/oauth/authorize?client_id=%s";

    @Value("${oauth.client.github.client-id}")
    private String clientId;

    @Value("${oauth.client.github.client-secret}")
    private String clientSecret;

    @Value("${oauth.client.github.token-url}")
    private String tokenUrl;

    @Value("${oauth.client.github.user-info-url}")
    private String userInfoUrl;

    public String fetchAccessToken(String code) {
        ClientResponse response = WebClient.create()
            .post().uri(tokenUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(new AccessTokenRequest(code, clientId, clientSecret)), AccessTokenRequest.class)
            .exchange()
            .block();

        validateResponse(response);

        AccessTokenResponse accessTokenResponse = response.bodyToMono(AccessTokenResponse.class).block();
        return Objects.requireNonNull(accessTokenResponse).getAccess_token();
    }

    private void validateResponse(ClientResponse response) {
        if (!response.statusCode().is2xxSuccessful()) {
            throw new RuntimeException("access token을 가져오는데 실패했습니다." + " response status code" + response.statusCode());
        }
    }

    public String fetchLoginUrl() {
        return String.format(GITHUB_LOGIN_URL_PREFIX, clientId);
    }

    public UserInfoResponse fetchUserInfo(String accessToken) {
        UserInfoResponse userInfoResponse = connectWithAuthorization(accessToken, userInfoUrl)
            .bodyToFlux(UserInfoResponse.class)
            .blockFirst();

        return Objects.requireNonNull(userInfoResponse);
    }

    private WebClient.ResponseSpec connectWithAuthorization(String accessToken, String url) {
        return WebClient.create()
            .get()
            .uri(url)
            .header("Authorization", String.format("token %s", accessToken))
            .retrieve();
    }
}
