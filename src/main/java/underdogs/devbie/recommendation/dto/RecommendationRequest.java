package underdogs.devbie.recommendation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.recommendation.domain.RecommendationType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RecommendationRequest {

    private RecommendationType recommendationType;
}
