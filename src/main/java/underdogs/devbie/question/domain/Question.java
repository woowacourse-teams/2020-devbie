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
import underdogs.devbie.config.BaseTimeEntity;
import underdogs.devbie.exception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Builder
    public Question(Long userId, QuestionTitle title, QuestionContent content) {
        validateParameters(userId, title, content);
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.visits = Visits.init();
    }

    private void validateParameters(Long userId, QuestionTitle title, QuestionContent content) {
        if (Objects.isNull(userId) | Objects.isNull(title) | Objects.isNull(content)) {
            throw new CreateFailException();
        }
    }

    public void updateQuestionInfo(QuestionTitle title, QuestionContent content) {
        this.title = title;
        this.content = content;
    }

    public void increaseVisits() {
        this.visits.increase();
    }
}
