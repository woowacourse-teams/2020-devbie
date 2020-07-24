package underdogs.devbie.question.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuestionResponses {

    List<QuestionResponse> responses = new ArrayList<>();

    public static QuestionResponses from(List<QuestionResponse> responses) {
        return new QuestionResponses(responses);
    }
}
