package underdogs.devbie.recommendation.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;

@Entity
@Table(indexes = @Index(name = "i_question_recommendation", columnList = "userId, questionId"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class QuestionRecommendation extends Recommendation {

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
