package underdogs.devbie.question.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.question.domain.Question;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class QuestionResponse {

    private Long questionId;
    private String title;
    private String content;

    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
            .questionId(question.getId())
            .title(question.getTitle().getTitle())
            .content(question.getContent().getContent())
            .build();
    }

    public static List<QuestionResponse> listFrom(List<Question> questions) {
        return questions.stream()
            .map(QuestionResponse::from)
            .collect(Collectors.toList());
    }
}
