package underdogs.devbie.answer.domain;

import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Answers {
    private List<Answer> answers;

    public static Answers from(List<Answer> answers) {
        return new Answers(answers);
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }
}
