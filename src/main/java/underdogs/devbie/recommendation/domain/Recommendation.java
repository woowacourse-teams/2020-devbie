package underdogs.devbie.recommendation.domain;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.config.BaseTimeEntity;
import underdogs.devbie.exception.CreateFailException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Recommendation extends BaseTimeEntity {

    private Long userId;

    private RecommendationType recommendationType;

    public Recommendation(Long userId, RecommendationType recommendationType) {
        validateParameters(userId, recommendationType);
        this.userId = userId;
        this.recommendationType = recommendationType;
    }

    private void validateParameters(Long userId, RecommendationType recommendationType) {
        if (Objects.isNull(userId) | Objects.isNull(recommendationType)) {
            throw new CreateFailException();
        }
    }

    public boolean hasRecommendationTypeOf(RecommendationType recommendationType) {
        return this.recommendationType.is(recommendationType);
    }

    public void toggleRecommended() {
        this.recommendationType = this.recommendationType.toggleType();
    }
}
