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

    @Builder
    public AnswerRecommendation(Long answerId, Long userId, RecommendationType recommendationType) {
        super(userId, recommendationType);
        validateParameters(answerId);
        this.answerId = answerId;
    }

    private void validateParameters(Long answerId) {
        if (Objects.isNull(answerId)) {
            throw new CreateFailException();
        }
    }
}