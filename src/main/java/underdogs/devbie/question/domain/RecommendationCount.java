package underdogs.devbie.question.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public class RecommendationCount {

    private Long recommendedCount;
    private Long nonRecommendedCount;

    public static RecommendationCount init() {
        return new RecommendationCount(0L, 0L);
    }

    public void increaseCount(RecommendationType type) {
        if (type == RecommendationType.RECOMMENDED) {
            this.recommendedCount++;
            return;
        }
        this.nonRecommendedCount++;
    }

    public void decreaseCount(RecommendationType type) {
        if (type == RecommendationType.RECOMMENDED) {
            this.recommendedCount--;
            return;
        }
        this.nonRecommendedCount--;
    }
}
