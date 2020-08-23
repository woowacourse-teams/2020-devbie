package underdogs.devbie.answer.dto;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.answer.domain.Answers;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AnswerResponses {

    private List<AnswerResponse> answerResponses;

    public static AnswerResponses of(Answers answers, List<User> users) {
        return IntStream.range(0, users.size())
            .mapToObj(i -> AnswerResponse.of(answers.getAnswers().get(i), users.get(i)))
            .collect(collectingAndThen(toList(), AnswerResponses::new));
    }
}
