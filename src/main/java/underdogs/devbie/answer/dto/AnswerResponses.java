package underdogs.devbie.answer.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.answer.domain.Answers;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AnswerResponses {

    private List<AnswerResponse> answerResponses;

    public static AnswerResponses from(Answers answers) {
        List<AnswerResponse> answerResponses = answers.getAnswers().stream()
            .map(AnswerResponse::from)
            .collect(Collectors.toList());
        return new AnswerResponses(answerResponses);
    }
}