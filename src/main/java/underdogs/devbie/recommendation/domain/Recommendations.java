package underdogs.devbie.recommendation.domain;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Recommendations {

    private List<? extends Recommendation> recommendations;

    public static Recommendations from(List<? extends Recommendation> recommendations) {
        return new Recommendations(recommendations);
    }

    public Long countRecommended() {
        return recommendations.stream()
            .filter(recommendation -> recommendation.hasRecommendationTypeOf(RecommendationType.RECOMMENDED))
            .count();
    }

    public Long countNonRecommended() {
        return recommendations.stream()
            .filter(recommendation -> recommendation.hasRecommendationTypeOf(RecommendationType.NON_RECOMMENDED))
            .count();
    }
}
