package underdogs.devbie.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.dto.JwtTokenResponse;
import underdogs.devbie.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login-url")
    public ResponseEntity<String> fetchLoginUrl() {
        return ResponseEntity.ok(authService.fetchLoginUrl());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> login(@RequestParam(value = "code") String code) {
        JwtTokenResponse jwtTokenResponse = authService.createToken(code);

        return ResponseEntity.ok(jwtTokenResponse);
    }
}
