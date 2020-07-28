package underdogs.devbie.recommendation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.recommendation.dto.RecommendationRequest;
import underdogs.devbie.recommendation.dto.RecommendationResponse;
import underdogs.devbie.recommendation.service.RecommendationService;
import underdogs.devbie.user.domain.User;

public abstract class RecommendationController {

    protected RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<RecommendationResponse> count(@RequestParam Long objectId) {
        RecommendationResponse recommendationResponse = recommendationService.count(objectId);

        return ResponseEntity.ok(recommendationResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createRecommendation(
        @RequestParam Long objectId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        recommendationService.createRecommendation(objectId, user.getId(), request.getRecommendationType());

        return ResponseEntity
            .created(URI.create("api/recommendation-object/" + objectId))
            .build();
    }

    @PatchMapping
    public ResponseEntity<Void> toggleRecommendation(
        @RequestParam Long objectId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        recommendationService.toggleRecommendation(objectId, user.getId(), request.getRecommendationType());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRecommendation(
        @RequestParam Long objectId,
        @LoginUser User user
    ) {
        recommendationService.deleteRecommendation(objectId, user.getId());

        return ResponseEntity.noContent().build();
    }

}
