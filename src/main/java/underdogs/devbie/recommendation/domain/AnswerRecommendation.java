package underdogs.devbie.recommendation.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.exception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AnswerRecommendation extends Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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