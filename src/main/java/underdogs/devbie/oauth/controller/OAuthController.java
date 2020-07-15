package underdogs.devbie.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import underdogs.devbie.oauth.service.OAuthService;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/login-url")
    public ResponseEntity<String> fetchLoginUrl() {
        return ResponseEntity.ok(oAuthService.fetchLoginUrl());
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(value = "code") String code) {
        // code => access token (github 제공)
        String accessToken = oAuthService.fetchAccessToken(code);

        // access token => user info


        // user info => user 저장

        // user info로 토큰 생성

        // 토큰 리턴
        return ResponseEntity.ok(accessToken);
    }
}