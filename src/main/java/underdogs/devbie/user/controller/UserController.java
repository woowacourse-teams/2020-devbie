package underdogs.devbie.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public ResponseEntity<UserResponse> findUser(@LoginUser User user) {
        return ResponseEntity.ok(UserResponse.from(user));
    }
}
