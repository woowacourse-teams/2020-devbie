package underdogs.devbie.recommendation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.auth.exception.InvalidAuthenticationException;
import underdogs.devbie.recommendation.dto.RecommendationCountResponse;
import underdogs.devbie.recommendation.dto.RecommendationRequest;
import underdogs.devbie.recommendation.dto.RecommendationResponse;
import underdogs.devbie.recommendation.service.RecommendationService;
import underdogs.devbie.user.domain.User;

public abstract class RecommendationController {

    protected RecommendationService recommendationService;

    @GetMapping(params = {"objectId", "userId"})
    public ResponseEntity<RecommendationResponse> getMyRecommendation(
        @RequestParam Long objectId,
        @RequestParam Long userId,
        @LoginUser User user
    ) {
        validateUser(userId, user);
        return ResponseEntity.ok(recommendationService.search(objectId, userId));
    }

    private void validateUser(Long userId, User user) {
        if (!user.getId().equals(userId)) {
            throw new InvalidAuthenticationException("인가되지 않는 요청입니다.");
        }
    }

    @NoValidate
    @GetMapping(params = {"objectId"})
    public ResponseEntity<RecommendationCountResponse> count(@RequestParam Long objectId) {
        RecommendationCountResponse recommendationCountResponse = recommendationService.count(objectId);

        return ResponseEntity.ok(recommendationCountResponse);
    }

    @PutMapping
    public ResponseEntity<Void> createOrUpdateRecommendation(
        @RequestParam Long objectId,
        @RequestBody RecommendationRequest request,
        @LoginUser User user
    ) {
        recommendationService.createOrUpdateRecommendation(objectId, user.getId(), request.getRecommendationType());

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
