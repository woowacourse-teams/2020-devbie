package underdogs.devbie.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationResponse {

    private Long recommendedCount;
    private Long nonRecommendedCount;
}
