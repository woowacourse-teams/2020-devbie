package underdogs.devbie.answer.domain;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Formula;

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

    @Formula("select count(*) from answer_recommendation a "
        + "where a.answer_id = answer_id and a.recommendation_type = 'RECOMMENDED'")
    private Long recommendedCount;

    @Formula("select count(*) from answer_recommendation a "
        + "where a.answer_id = answer_id and a.recommendation_type = 'NON_RECOMMENDED'")
    private Long nonRecommendedCount;

    public static RecommendationCount init() {
        return new RecommendationCount(0L, 0L);
    }
}
