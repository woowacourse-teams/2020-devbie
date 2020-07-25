package underdogs.devbie.answer.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.AnswerContent;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AnswerCreateRequest {

    @NotNull(message = "Question ID가 존재하지 않습니다.")
    private Long questionId;

    @NotEmpty(message = "본문이 비어있습니다.")
    private String content;

    public static AnswerCreateRequest of(Long questionId, String content) {
        return new AnswerCreateRequest(questionId, content);
    }

    public Answer toEntity(Long userId) {
        return Answer.builder()
            .userId(userId)
            .questionId(questionId)
            .content(AnswerContent.from(content))
            .build();
    }
}
