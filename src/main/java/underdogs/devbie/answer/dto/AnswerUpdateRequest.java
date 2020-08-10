package underdogs.devbie.answer.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AnswerUpdateRequest {

    @NotEmpty
    private String content;

    public static AnswerUpdateRequest from(String content) {
        return new AnswerUpdateRequest(content);
    }
}
