package underdogs.devbie.recommendation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.recommendation.domain.Recommendations;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationCountResponse {

    private Long recommendedCount;
    private Long nonRecommendedCount;

    public static RecommendationCountResponse from(Recommendations recommendations) {
        Long recommendationCount = recommendations.countRecommended();
        Long nonRecommendationCount = recommendations.countNonRecommended();

        return new RecommendationCountResponse(recommendationCount, nonRecommendationCount);
    }
}
