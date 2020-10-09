package underdogs.devbie.answer.domain;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.config.BaseTimeEntity;
import exception.CreateFailException;
import underdogs.devbie.recommendation.domain.RecommendationCount;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long questionId;

    @Embedded
    private AnswerContent content;

    @Embedded
    private RecommendationCount recommendationCount;

    @Builder
    public Answer(Long id, Long userId, Long questionId, AnswerContent content) {
        validateParameters(userId, questionId, content);
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.content = content;
        this.recommendationCount = RecommendationCount.init();
    }

    private void validateParameters(Long userId, Long questionId, AnswerContent content) {
        if (Objects.isNull(userId) || Objects.isNull(questionId) || Objects.isNull(content)) {
            throw new CreateFailException();
        }
    }

    public void updateContent(AnswerContent content) {
        this.content = content;
    }

    public boolean isNotMatched(Long userId) {
        return !this.userId.equals(userId);
    }

    public void increaseRecommendationCount(RecommendationType recommendationType) {
        recommendationCount.increaseRecommendationCount(recommendationType);
    }

    public void decreaseRecommendationCount(RecommendationType recommendationType) {
        recommendationCount.decreaseRecommendationCount(recommendationType);
    }
}
