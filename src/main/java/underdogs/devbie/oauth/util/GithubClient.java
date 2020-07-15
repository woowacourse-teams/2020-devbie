package underdogs.devbie.oauth.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import underdogs.devbie.oauth.service.dto.AccessTokenRequest;
import underdogs.devbie.oauth.service.dto.AccessTokenResponse;

import java.util.Objects;

@Getter
@Component
public class GithubClient {

    private static final String GITHUB_LOGIN_URL_PREFIX = "https://github.com/login/oauth/authorize?client_id=";

    @Value("${oauth.client.github.client-id}")
    private String clientId;

    @Value("${oauth.client.github.client-secret}")
    private String clientSecret;

    @Value("${oauth.client.github.token-url}")
    private String tokenUrl;

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
            throw new RuntimeException("access token을 가져오는데 실패했습니다."
                    + " response status code" + response.statusCode());
        }
    }

    public String fetchLoginUrl() {
        return GITHUB_LOGIN_URL_PREFIX + clientId;
    }
}
