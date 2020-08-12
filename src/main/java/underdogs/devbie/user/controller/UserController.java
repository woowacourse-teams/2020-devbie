package underdogs.devbie.user.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserCreateRequest;
import underdogs.devbie.user.dto.UserResponse;
import underdogs.devbie.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer devieToken", required = true, dataType = "String", paramType = "header")})
    @GetMapping
    public ResponseEntity<UserResponse> findUser(@LoginUser User user) {
        return ResponseEntity
            .ok(UserResponse.from(user));
    }

    @NoValidate
    @PostMapping
    public ResponseEntity<Long> saveUser(@Valid @RequestBody UserCreateRequest request) {
        Long userId = userService.saveWithoutOAuthId(request);
        return ResponseEntity
            .created(URI.create(String.format("/api/users/%d", userId)))
            .body(userId);
    }

    @NoValidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
