package underdogs.devbie.answer.domain;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class AnswerContent {

    @Lob
    private String content;

    public static AnswerContent from(String content) {
        return new AnswerContent(content);
    }
}
