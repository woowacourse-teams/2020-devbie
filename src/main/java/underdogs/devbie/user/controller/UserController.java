package underdogs.devbie.user.controller;

import java.io.IOException;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.aws.S3Service;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserCreateRequest;
import underdogs.devbie.user.dto.UserResponse;
import underdogs.devbie.user.dto.UserUpdateImageRequest;
import underdogs.devbie.user.dto.UserUpdateInfoRequest;
import underdogs.devbie.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;

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

    @PatchMapping("/me")
    public ResponseEntity<Void> updateUser(
        @LoginUser User user,
        @Valid @RequestBody UserUpdateInfoRequest request
    ) {
        userService.updateUserInfo(user, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateImage(
        @LoginUser User user,
        @Valid @ModelAttribute UserUpdateImageRequest request
    ) throws IOException {
        String imagePath = s3Service.upload(request.getImage());
        userService.updateUserImage(user, imagePath);
        return ResponseEntity.noContent().build();
    }

    @NoValidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
