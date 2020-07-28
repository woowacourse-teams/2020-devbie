package underdogs.devbie.recommendation.dto;

import static java.util.stream.Collectors.*;
import static underdogs.devbie.recommendation.domain.RecommendationType.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@AllArgsConstructor
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
