package underdogs.devbie.question.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.Role;
import underdogs.devbie.question.dto.HashtagCreateRequest;
import underdogs.devbie.question.service.HashtagService;
import underdogs.devbie.user.domain.RoleType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hashtags")
public class HashtagController {

    private final HashtagService hashtagService;

    @Role(role = {RoleType.ADMIN})
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody HashtagCreateRequest request) {
        Long hashtagId = hashtagService.save(request);
        return ResponseEntity
            .created(URI.create("/api/hashtags/" + hashtagId))
            .build();
    }
}
