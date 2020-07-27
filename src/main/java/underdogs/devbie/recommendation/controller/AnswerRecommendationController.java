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
import underdogs.devbie.recommendation.service.AnswerRecommendationService;
import underdogs.devbie.user.domain.User;

@RestController
@RequestMapping("/api/recommendation-answer")
@RequiredArgsConstructor
public class AnswerRecommendationController {

    private final AnswerRecommendationService answerRecommendationService;

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationResponse> count(@PathVariable("id") Long answerId) {
        RecommendationResponse recommendationResponse = answerRecommendationService.count(answerId);

        return ResponseEntity.ok(recommendationResponse);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createRecommendation(
        @PathVariable("id") Long answerId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        answerRecommendationService.createRecommendation(answerId, user.getId(), request.getRecommendationType());

        return ResponseEntity
            .created(URI.create(String.format("api/recommendation/answer/%d", answerId)))
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> toggleRecommendation(
        @PathVariable("id") Long answerId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        answerRecommendationService.toggleRecommendation(answerId, user.getId(), request.getRecommendationType());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(
        @PathVariable("id") Long answerId,
        @LoginUser User user
    ) {
        answerRecommendationService.deleteRecommendation(answerId, user.getId());

        return ResponseEntity.noContent().build();
    }
}
