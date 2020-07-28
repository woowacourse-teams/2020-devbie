package underdogs.devbie.recommendation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.recommendation.dto.RecommendationRequest;
import underdogs.devbie.recommendation.dto.RecommendationResponse;
import underdogs.devbie.recommendation.service.RecommendationService;
import underdogs.devbie.user.domain.User;

public abstract class RecommendationController {

    protected RecommendationService recommendationService;

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationResponse> count(@PathVariable("id") Long objectId) {
        RecommendationResponse recommendationResponse = recommendationService.count(objectId);

        return ResponseEntity.ok(recommendationResponse);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createRecommendation(
        @PathVariable("id") Long objectId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        recommendationService.createRecommendation(objectId, user.getId(), request.getRecommendationType());

        return ResponseEntity
            .created(URI.create("api/recommendation-object/" + objectId))
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> toggleRecommendation(
        @PathVariable("id") Long objectId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        recommendationService.toggleRecommendation(objectId, user.getId(), request.getRecommendationType());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(
        @PathVariable("id") Long objectId,
        @LoginUser User user
    ) {
        recommendationService.deleteRecommendation(objectId, user.getId());

        return ResponseEntity.noContent().build();
    }

}
