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
        System.out.println(answers + ">>>>>>>>>>>>");
        System.out.println(answers.get(0).getRecommendationCount().getRecommendedCount() + ">>>>>>>>>>>>");
        return new Answers(answers);
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }
}
