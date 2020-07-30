package underdogs.devbie.question.controller;

import java.net.URI;

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

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.dto.QuestionUpdateRequest;
import underdogs.devbie.question.service.QuestionService;
import underdogs.devbie.user.domain.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Void> save(
        @LoginUser User user,
        @Valid @RequestBody QuestionCreateRequest request) {
        Long questionId = questionService.save(user.getId(), request);
        return ResponseEntity
            .created(URI.create(String.format("/api/questions/%d", questionId)))
            .build();
    }

    @NoValidate
    @GetMapping
    public ResponseEntity<QuestionResponses> readAll() {
        QuestionResponses responses = questionService.readAll();
        return ResponseEntity
            .ok(responses);
    }

    @NoValidate
    @GetMapping(params = "keyword")
    public ResponseEntity<QuestionResponses> searchByTitle(
        @RequestParam("keyword") String keyword
    ) {
        QuestionResponses responses = questionService.searchByTitle(keyword);
        return ResponseEntity
            .ok(responses);
    }

    @NoValidate
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> read(@PathVariable("id") Long id) {
        QuestionResponse response = questionService.read(id);
        return ResponseEntity
            .ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
        @LoginUser User user,
        @PathVariable("id") Long id,
        @Valid @RequestBody QuestionUpdateRequest request
    ) {
        questionService.update(user.getId(), id, request);
        return ResponseEntity
            .noContent()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @LoginUser User user,
        @PathVariable("id") Long id
    ) {
        questionService.delete(user.getId(), id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
