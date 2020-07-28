package underdogs.devbie.recommendation.dto;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.recommendation.domain.Recommendation;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationResponse {

    private String recommendationType;

    public static RecommendationResponse from(Optional<Recommendation> recommendation) {
        String recommendationType = recommendation
            .map(r -> r.getRecommendationType().name())
            .orElse("NOT_EXIST");

        return new RecommendationResponse(recommendationType);
    }
}
