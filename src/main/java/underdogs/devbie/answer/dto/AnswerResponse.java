package underdogs.devbie.answer.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.answer.domain.Answer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AnswerResponse {

    private Long id;
    private Long userId;
    private Long questionId;
    private String content;

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(answer.getId(), answer.getUserId(), answer.getQuestionId(),
            answer.getContent().getContent());
    }
}
