package underdogs.devbie.recommendation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.recommendation.dto.RecommendationRequest;
import underdogs.devbie.recommendation.dto.RecommendationResponse;
import underdogs.devbie.recommendation.service.QuestionRecommendationService;
import underdogs.devbie.user.domain.User;

@RestController
@RequestMapping("/api/recommendation-question")
@RequiredArgsConstructor
public class QuestionRecommendationController {

    private final QuestionRecommendationService questionRecommendationService;

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationResponse> count(@PathVariable("id") Long questionId) {
        RecommendationResponse recommendationResponse = questionRecommendationService.count(questionId);

        return ResponseEntity.ok(recommendationResponse);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createRecommendation(
        @PathVariable("id") Long questionId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        questionRecommendationService.createRecommendation(questionId, user.getId(), request.getRecommendationType());

        return ResponseEntity
            .created(URI.create("api/recommendation/question/" + questionId))
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> toggleRecommendation(
        @PathVariable("id") Long questionId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        questionRecommendationService.toggleRecommendation(questionId, user.getId(), request.getRecommendationType());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(
        @PathVariable("id") Long questionId,
        @LoginUser User user
    ) {
        questionRecommendationService.deleteRecommendation(questionId, user.getId());

        return ResponseEntity.noContent().build();
    }
}
