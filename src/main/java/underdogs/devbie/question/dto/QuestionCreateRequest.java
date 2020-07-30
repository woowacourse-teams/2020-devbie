package underdogs.devbie.question.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionContent;
import underdogs.devbie.question.domain.QuestionTitle;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class QuestionCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Question toEntity(Long userId) {
        return Question.builder()
            .userId(userId)
            .title(QuestionTitle.from(title))
            .content(QuestionContent.from(content))
            .build();
    }
}
