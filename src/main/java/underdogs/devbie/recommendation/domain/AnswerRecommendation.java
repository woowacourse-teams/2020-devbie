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
@Table(indexes = @Index(name = "i_notice_recommendation", columnList = "user_id, notice_id"))
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
