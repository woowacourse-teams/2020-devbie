package underdogs.devbie.recommendation.domain;

import java.util.Objects;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.common.BaseTimeEntity;
import underdogs.devbie.exception.CreateFailException;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Recommendation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Long userId;

    @Enumerated(EnumType.STRING)
    private RecommendationType recommendationType;

    public Recommendation(Long userId, RecommendationType recommendationType) {
        validateParameters(userId, recommendationType);
        this.userId = userId;
        this.recommendationType = recommendationType;
    }

    private void validateParameters(Long userId, RecommendationType recommendationType) {
        if (Objects.isNull(userId) || Objects.isNull(recommendationType)) {
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
