package underdogs.devbie.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {
    @Value("${oauth.client.github.client-id}")
    private String clientId;

    @GetMapping("/login-url")
    public ResponseEntity<String> getLoginUrl() {
        String loginUrl = "https://github.com/login/oauth/authorize?client_id=" + clientId;
        return ResponseEntity.ok(loginUrl);
    }

}