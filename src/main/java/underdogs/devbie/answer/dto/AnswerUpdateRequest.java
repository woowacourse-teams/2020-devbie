package underdogs.devbie.answer.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AnswerUpdateRequest {

    @NotBlank
    private String content;

    public static AnswerUpdateRequest from(String content) {
        return new AnswerUpdateRequest(content);
    }
}
