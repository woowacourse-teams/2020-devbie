package underdogs.devbie.user.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.aws.S3Service;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserCreateRequest;
import underdogs.devbie.user.dto.UserResponse;
import underdogs.devbie.user.dto.UserUpdateRequest;
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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(
        @LoginUser User user,
        @PathVariable("id") Long userId,
        @Valid @RequestBody UserUpdateRequest request
    ) {
        userService.updateUserInfo(user, userId ,request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Void> updateImage(
        @LoginUser User user,
        @PathVariable("id") Long userId,
        @RequestParam("image") MultipartFile imageFile
    ) throws IOException {
        if (!Objects.isNull(imageFile)) {
            String imagePath = s3Service.upload(imageFile);
            userService.updateUserImage(user, userId, imagePath);
        }
        return ResponseEntity.noContent().build();
    }

    @NoValidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
