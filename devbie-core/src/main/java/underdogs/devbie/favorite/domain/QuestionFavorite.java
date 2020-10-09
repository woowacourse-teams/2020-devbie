package underdogs.devbie.favorite.domain;

import java.util.Objects;

import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class QuestionFavorite extends Favorite {

    private Long questionId;

    public QuestionFavorite(Long userId, Long questionId) {
        super(userId);
        this.questionId = questionId;
    }

    public static QuestionFavorite of(Long questionId, Long userId) {
        validateParameters(questionId);
        return new QuestionFavorite(userId, questionId);
    }

    private static void validateParameters(Long questionId) {
        if (Objects.isNull(questionId)) {
            throw new CreateFailException();
        }
    }
}
