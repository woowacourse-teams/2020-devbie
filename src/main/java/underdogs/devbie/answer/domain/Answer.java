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
import underdogs.devbie.config.BaseTimeEntity;
import underdogs.devbie.exception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long questionId;

    @Embedded
    private AnswerContent content;

    @Builder
    public Answer(Long userId, Long questionId, AnswerContent content) {
        validateParameters(userId, questionId, content);
        this.userId = userId;
        this.questionId = questionId;
        this.content = content;
    }

    private void validateParameters(Long userId, Long questionid, AnswerContent content) {
        if (Objects.isNull(userId) | Objects.isNull(questionid) | Objects.isNull(content)) {
            throw new CreateFailException();
        }
    }

    public void updateContent(AnswerContent content) {
        this.content = content;
    }
}
