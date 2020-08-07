package underdogs.devbie.question.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Question;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class QuestionResponses {

    private List<QuestionResponse> questions = new ArrayList<>();

    public static QuestionResponses from(List<Question> questions) {
        return new QuestionResponses(questions.stream()
            .map(QuestionResponse::from)
            .collect(Collectors.toList()));
    }
}
