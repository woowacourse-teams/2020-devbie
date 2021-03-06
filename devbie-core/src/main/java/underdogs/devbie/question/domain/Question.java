package underdogs.devbie.question.domain;

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
import underdogs.devbie.common.BaseTimeEntity;
import underdogs.devbie.exception.CreateFailException;
import underdogs.devbie.recommendation.domain.RecommendationCount;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Embedded
    private QuestionTitle title;

    @Embedded
    private QuestionContent content;

    @Embedded
    private Visits visits;

    @Embedded
    private RecommendationCount recommendationCount;

    @Embedded
    private QuestionHashtags hashtags;

    @Builder
    public Question(Long id, Long userId, QuestionTitle title, QuestionContent content, QuestionHashtags hashtags) {
        validateParameters(userId, title, content);
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
        this.visits = Visits.init();
        this.recommendationCount = RecommendationCount.init();
    }

    private void validateParameters(Long userId, QuestionTitle title, QuestionContent content) {
        if (Objects.isNull(userId) || Objects.isNull(title) || Objects.isNull(content)) {
            throw new CreateFailException();
        }
    }

    public void updateQuestionInfo(Question question) {
        this.title = question.title;
        this.content = question.content;
    }

    public void increaseVisits() {
        this.visits.increase();
    }

    public void setHashtags(QuestionHashtags hashtags) {
        this.hashtags.setHashtags(hashtags.getQuestionHashtags());
    }

    public void increaseRecommendationCount(RecommendationType recommendationType) {
        recommendationCount.increaseRecommendationCount(recommendationType);
    }

    public void decreaseRecommendationCount(RecommendationType recommendationType) {
        recommendationCount.decreaseRecommendationCount(recommendationType);
    }
}
