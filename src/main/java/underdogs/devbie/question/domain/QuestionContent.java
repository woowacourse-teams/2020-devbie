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
public class QuestionContent {

    private String content;

    public static QuestionContent from(String content) {
        if (content.isEmpty()) {
            throw new QuestionNotMeetingEssentialsException(content + "이 빈값입니다.");
        }
        return new QuestionContent(content);
    }
}
