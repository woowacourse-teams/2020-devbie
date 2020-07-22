package underdogs.devbie.question.domain;

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
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long userId;

    private Long visits;

    @Builder
    public Question(String title, String content, Long userId) {
        validateParameters(title, content, userId);
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.visits = 0L;
    }

    private void validateParameters(String title, String content, Long userId) {
        if (Objects.isNull(title) | Objects.isNull(content) | Objects.isNull(userId)) {
            throw new CreateFailException();
        }
    }

    public void updateQuestionInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseVisits() {
        this.visits++;
    }
}
