package underdogs.devbie.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserResponse;

@RestController
public class UserController {

	@GetMapping("/api/user")
	public ResponseEntity<UserResponse> findUser(@LoginUser User user) {
		return ResponseEntity.ok(UserResponse.from(user));
	}
}
