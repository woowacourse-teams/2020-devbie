package underdogs.devbie.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.service.AuthService;
import underdogs.devbie.auth.service.dto.UserInfoResponse;
import underdogs.devbie.user.service.UserService;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/login-url")
    public ResponseEntity<String> fetchLoginUrl() {
        return ResponseEntity.ok(authService.fetchLoginUrl());
    }

    @GetMapping("/login")
    public ResponseEntity<UserInfoResponse> login(@RequestParam(value = "code") String code) {
        // code => access token (github 제공)
        String accessToken = authService.fetchAccessToken(code);

        // access token => user info
        UserInfoResponse userInfoResponse = authService.fetchUserInfo(accessToken);

        // user info => user 저장
        userService.saveOrUpdateUser(userInfoResponse);

        // user info로 토큰 생성

        // 토큰 리턴
        return ResponseEntity.ok(userInfoResponse);
    }
}