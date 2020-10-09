package underdogs.devbie.question.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.exception.QuestionNotMeetingEssentialsException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public class QuestionTitle {

    private String title;

    public static QuestionTitle from(String title) {
        if (title.isEmpty()) {
            throw new QuestionNotMeetingEssentialsException(title + "이 빈값입니다.");
        }
        return new QuestionTitle(title);
    }
}
