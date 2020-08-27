package underdogs.devbie.question.domain;

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

    @Formula(value="(select count(*) from question_recommendation q "
        + "where q.question_id = question_id and q.recommendation_type = 'RECOMMENDED')")
    private Long recommendedCount;

    @Formula(value="(select count(*) from question_recommendation q "
        + "where q.question_id = question_id and q.recommendation_type = 'NON_RECOMMENDED')")
    private Long nonRecommendedCount;

    public static RecommendationCount init() {
        return new RecommendationCount(0L, 0L);
    }
}
