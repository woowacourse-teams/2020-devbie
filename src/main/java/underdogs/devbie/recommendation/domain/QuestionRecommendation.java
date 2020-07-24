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
public class QuestionRecommendation extends Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionId;

    private QuestionRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        super(userId, recommendationType);
        this.questionId = questionId;
    }

    public static QuestionRecommendation of(Long questionId, Long userId, RecommendationType recommendationType) {
        validateParameters(questionId);
        return new QuestionRecommendation(questionId, userId, recommendationType);
    }

    private static void validateParameters(Long questionId) {
        if (Objects.isNull(questionId)) {
            throw new CreateFailException();
        }
    }
}