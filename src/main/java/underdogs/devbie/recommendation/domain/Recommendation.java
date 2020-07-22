package underdogs.devbie.recommendation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Recommendation {

    private Long userId;

    private RecommendationType recommendationType;

    public boolean hasRecommendationTypeOf(RecommendationType recommendationType) {
        return this.recommendationType.is(recommendationType);
    }

    public void toggleRecommended() {
        this.recommendationType = this.recommendationType.toggleType();
    }
}
