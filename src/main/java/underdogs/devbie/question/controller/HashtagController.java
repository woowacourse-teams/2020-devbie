package underdogs.devbie.question.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.Role;
import underdogs.devbie.question.dto.HashtagCreateRequest;
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.HashtagResponses;
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

    @GetMapping
    public ResponseEntity<HashtagResponses> readAll() {
        HashtagResponses hashtagResponses = hashtagService.readAll();
        return ResponseEntity
            .ok(hashtagResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashtagResponse> read(@PathVariable("id") Long id) {
        HashtagResponse hashtagResponse = hashtagService.read(id);
        return ResponseEntity
            .ok(hashtagResponse);
    }

    @GetMapping(params = "tagName")
    public ResponseEntity<HashtagResponse> read(@RequestParam("tagName") String tagName) {
        HashtagResponse hashtagResponse = hashtagService.readByTagName(tagName);
        return ResponseEntity
            .ok(hashtagResponse);
    }
}
