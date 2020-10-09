package underdogs.devbie.answer.domain;

import java.util.Objects;

import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class AnswerRecommendation extends Recommendation {

    private Long answerId;

    private AnswerRecommendation(Long answerId, Long userId, RecommendationType recommendationType) {
        super(userId, recommendationType);
        this.answerId = answerId;
    }

    public static AnswerRecommendation of(Long answerId, Long userId, RecommendationType recommendationType) {
        validateParameters(answerId);
        return new AnswerRecommendation(answerId, userId, recommendationType);
    }

    private static void validateParameters(Long answerId) {
        if (Objects.isNull(answerId)) {
            throw new CreateFailException();
        }
    }
}
