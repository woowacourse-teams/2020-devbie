package underdogs.devbie.recommendation.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public void increaseRecommendationCount(RecommendationType recommendationType) {
        if (recommendationType.is(RecommendationType.RECOMMENDED)) {
            recommendedCount ++;
            return;
        }
        nonRecommendedCount ++;
    }

    public void decreaseRecommendationCount(RecommendationType recommendationType) {
        if (recommendationType.is(RecommendationType.RECOMMENDED)) {
            recommendedCount --;
            return;
        }
        nonRecommendedCount --;
    }
}
