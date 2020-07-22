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

    private String title;

    private String content;

    private Long visits;

    @Builder
    public Question(Long userId, String title, String content) {
        validateParameters(userId, title, content);
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.visits = 0L;
    }

    private void validateParameters(Long userId, String title, String content) {
        if (Objects.isNull(userId) | Objects.isNull(title) | Objects.isNull(content)) {
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
