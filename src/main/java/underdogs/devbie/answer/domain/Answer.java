package underdogs.devbie.answer.domain;

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
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long questionId;
    private String content;

    @Builder
    public Answer(Long userId, Long questionId, String content) {
        validateParameters(userId, questionId, content);
        this.userId = userId;
        this.questionId = questionId;
        this.content = content;
    }

    private void validateParameters(Long userId, Long questionid, String content) {
        if (Objects.isNull(userId) | Objects.isNull(questionid) | Objects.isNull(content)) {
            throw new CreateFailException();
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
